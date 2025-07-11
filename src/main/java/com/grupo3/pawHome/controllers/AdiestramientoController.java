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

@Slf4j
@Controller
public class AdiestramientoController {
    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final StripeService stripeService;
    private final SecurityUtil securityUtil;

    public AdiestramientoController(UsuarioService usuarioService, ProductoService productoService, StripeService stripeService, SecurityUtil securityUtil) {
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.stripeService = stripeService;
        this.securityUtil = securityUtil;
    }

    // Metodo para mostrar la pagina de adiestramiento
    @GetMapping("/adiestramiento")
    public String mostrarAdiestramiento(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        if(userDetails == null) {
            model.addAttribute("usuario", null);
            return "adiestramiento";
        }
        Usuario usuario = userDetails.getUsuario();
        if(usuario.getPerfilDatos() == null) {
            model.addAttribute("usuario", null);
            return "adiestramiento";
        }
        model.addAttribute("usuario", usuario);
        return "adiestramiento";
    }

    @PostMapping("/adiestramiento/checkoutAdiestramiento")
    public ResponseEntity<StripeResponse> checkoutDesdeAdiestramiento(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestBody AdiestramientoCheckoutRequest request, // Nuevo DTO
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

        Optional<Producto> productoOpt = productoService.findByNombre(request.nombreProducto());

        if (productoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Producto no encontrado")
                            .build());
        }

        Producto producto = productoOpt.get();

        // Crear ítem de carrito específico para adiestramiento
        ItemCarritoDTO items =
                new ItemCarritoDTO(
                        producto,
                        request.cantidadDias(),
                        producto.getTarifas().getFirst().getPrecioUnitario() // Obtener última tarifa

                );

        // Proceso de Stripe (igual que en tienda)
        ProductRequest productRequest = new ProductRequest((long)(items.getPrecioUnitario()*100), (long) items.getCantidad(), items.getProducto().getNombre(), "eur");
        List<ProductRequest> productRequests = List.of(productRequest);
        Usuario user = usuarioService.ensureStripeCustomerExists(usuario);
        StripeResponse response = stripeService.checkoutProducts(
                productRequests,
                user.getStripeCustomerId()
        );
        securityUtil.updateAuthenticatedUser(user);
        session.setAttribute("motivo", "Servicio");
        session.setAttribute("itemServicio", items);
        session.setAttribute("nombreServicio", "Servicio de adiestramiento");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // DTO para la request
    public record AdiestramientoCheckoutRequest(
            String nombreProducto,
            int cantidadDias
    ) {}
}
