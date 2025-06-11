package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.grupo3.pawHome.entities.Producto;
import com.grupo3.pawHome.entities.Talla;
import com.grupo3.pawHome.entities.Tarifa;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.ProductoService;
import com.grupo3.pawHome.services.StripeService;
import com.grupo3.pawHome.services.TarifaService;
import com.grupo3.pawHome.services.UsuarioService;
import com.grupo3.pawHome.util.SecurityUtil;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class GuarderiaController {
    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final StripeService stripeService;
    private final SecurityUtil securityUtil;

    public GuarderiaController(UsuarioService usuarioService, ProductoService productoService, StripeService stripeService, SecurityUtil securityUtil) {
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.stripeService = stripeService;
        this.securityUtil = securityUtil;
    }

    // Metodo para mostrar la pagina de la guarderia
    @GetMapping("/guarderia")
    public String mostrarGuarderia(@AuthenticationPrincipal Usuario usuario, Model model) {
        model.addAttribute("usuario", usuario);
        return "guarderia";
    }

//    @PostMapping("/guarderia/checkoutGuarderia")
//    public ResponseEntity<StripeResponse> checkoutDesdeGuarderia(
//            @AuthenticationPrincipal Usuario usuario,
//            @RequestBody GuarderiaCheckoutRequest request, // Nuevo DTO
//            HttpSession session) throws StripeException {
//
//        if (usuario == null) {
//            return ResponseEntity.badRequest().body(
//                    StripeResponse.builder()
//                            .status("FAILED")
//                            .message("Usuario no autenticado")
//                            .build());
//        }
//
//        Optional<Producto> producto = productoService.findByNombre(request.nombreProducto());
//
//        if (producto.isEmpty()) {
//            return ResponseEntity.badRequest().body(
//                    StripeResponse.builder()
//                            .status("FAILED")
//                            .message("Producto no encontrado")
//                            .build());
//        }
//
//        // Crear ítem de carrito específico para guardería
//        List<ItemCarritoDTO> items = Arrays.asList(
//                new ItemCarritoDTO(
//                        producto.get(),
//                        request.cantidadDias(),
//                        producto.get().getTarifas().getFirst().getPrecioUnitario() // Obtener última tarifa
//                )
//        );
//
//        // Proceso de Stripe (igual que en tienda)
//        List<ProductRequest> productRequests = stripeService.convertirCarritoAProductRequests(items);
//        Usuario user = usuarioService.ensureStripeCustomerExists(usuario);
//        StripeResponse response = stripeService.checkoutProducts(
//                productRequests,
//                user.getStripeCustomerId()
//        );
//        log.info("Estamos ok");
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    @PostMapping("/guarderia/checkoutGuarderia")
    public ResponseEntity<StripeResponse> checkoutDesdeGuarderia(
            @AuthenticationPrincipal Usuario usuario,
            @RequestBody GuarderiaCheckoutRequest request, // Nuevo DTO
            HttpSession session) throws StripeException {

        if (usuario == null) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Usuario no autenticado")
                            .build());
        }

        Optional<Producto> producto = productoService.findByNombre(request.nombreProducto());

        if (producto.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Producto no encontrado")
                            .build());
        }

        // Crear ítem de carrito específico para guardería
        ItemCarritoDTO items =
                new ItemCarritoDTO(
                        producto.get(),
                        request.cantidadDias(),
                        producto.get().getTarifas().getFirst().getPrecioUnitario() // Obtener última tarifa

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
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // DTO para la request
    public record GuarderiaCheckoutRequest(
            String nombreProducto,
            int cantidadDias
    ) {}

}
