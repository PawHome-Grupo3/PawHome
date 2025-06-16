package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.grupo3.pawHome.entities.Producto;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.ProductoService;
import com.grupo3.pawHome.services.StripeService;
import com.grupo3.pawHome.services.UsuarioService;
import com.grupo3.pawHome.util.SecurityUtil;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class PeluqueriaController {

    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final StripeService stripeService;
    private final SecurityUtil securityUtil;

    public PeluqueriaController(UsuarioService usuarioService, ProductoService productoService, StripeService stripeService, SecurityUtil securityUtil) {
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.stripeService = stripeService;
        this.securityUtil = securityUtil;
    }

    // Metodo para mostrar la pagina de la peluqueria
    @GetMapping("/peluqueria")
    public String mostrarGuarderia(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        if(userDetails == null) {
            model.addAttribute("usuario", null);
            return "peluqueria";
        }
        Usuario usuario = userDetails.getUsuario();
        if(usuario.getPerfilDatos() == null) {
            model.addAttribute("usuario", null);
            return "peluqueria";
        }
        model.addAttribute("usuario", usuario);
        return "peluqueria";
    }

    @PostMapping("/peluqueria/checkoutPeluqueria")
    public ResponseEntity<StripeResponse> checkoutDesdePeluqueria(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestBody PeluqueriaCheckoutRequest request,
            HttpSession session) throws StripeException {

        if (userDetails == null) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Usuario no autenticado")
                            .build());
        }

        Usuario usuario = userDetails.getUsuario();

        if (usuario.getPerfilDatos() == null) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Datos de Perfil Incompletos")
                            .build()
            );
        }

        // Aquí puedes buscar los productos por nombre y construir la lista de ItemCarritoDTO
        List<String> nombresProductos = request.productos();

        // Creamos una lista de productos Optional a partir de la lista de los nombres
        List<Optional<Producto>> productosOpt = nombresProductos.stream()
                .map(productoService::findByNombre)
                .toList();

        if (productosOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Producto no encontrado")
                            .build());
        }

        List<Producto> productos = productosOpt.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();


        // Crear ítem de carrito específico para guardería
        List<ItemCarritoDTO> carrito = productos.stream()
                .map(producto -> new ItemCarritoDTO(
                        producto,
                        1, // cantidad siempre 1
                        producto.getTarifas().getFirst().getPrecioUnitario()
                ))
                .toList();

        // Proceso de Stripe (igual que en tienda)
        List<ProductRequest> productRequests = stripeService.convertirCarritoAProductRequests(carrito);
        Usuario user = usuarioService.ensureStripeCustomerExists(usuario);
        StripeResponse response = stripeService.checkoutProducts(
                productRequests,
                user.getStripeCustomerId()
        );
        securityUtil.updateAuthenticatedUser(user);
        session.setAttribute("motivo", "ServicioPeluqueria");
        session.setAttribute("carrito", carrito);
        session.setAttribute("nombreServicio", "Servicio de peluqueria");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public record PeluqueriaCheckoutRequest(
            List<String> productos
    ) { }
}
