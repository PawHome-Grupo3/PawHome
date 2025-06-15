package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.StripeService;
import com.grupo3.pawHome.services.UsuarioService;
import com.grupo3.pawHome.util.SecurityUtil;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ColaboraController {

    private final UsuarioService usuarioService;
    private final StripeService stripeService;
    private final SecurityUtil securityUtil;

    public ColaboraController(UsuarioService usuarioService, StripeService stripeService, SecurityUtil securityUtil) {
        this.usuarioService = usuarioService;
        this.stripeService = stripeService;
        this.securityUtil = securityUtil;
    }

    @GetMapping("/colabora/donaciones-materiales")
    public String mostrarColaboraDonaMateriales()
    {
        return "donarMateriales";
    }

    @GetMapping("/colabora/dona")
    public String mostrarColaboraDona()
    {
        return "Dona";
    }

    @PostMapping("/colabora/dona/checkout")
    public ResponseEntity<StripeResponse> checkoutDesdeDona(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestBody Map<String, Object> datos,
            HttpSession session
    ) throws StripeException {

        if (userDetails == null) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Usuario no autenticado. Por favor inicia sesión")
                            .build()
            );
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

        double precio = Double.parseDouble(datos.get("cantidad").toString());
        long precioRequest = (long) (precio * 100);

        ProductRequest productRequest = new ProductRequest(precioRequest, (long)1, "donacion", "eur");
        List<ProductRequest> productRequests = new ArrayList<>();
        productRequests.add(productRequest);

        session.setAttribute("precio", precio);

        Usuario userCustomerId = usuarioService.ensureStripeCustomerExists(usuario);
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequests, userCustomerId.getStripeCustomerId());
        securityUtil.updateAuthenticatedUser(userCustomerId);

        session.setAttribute("motivo", "Donacion");

        return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
    }
}
