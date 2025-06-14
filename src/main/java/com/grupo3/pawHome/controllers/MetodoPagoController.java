package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.config.StripeConfig;
import com.grupo3.pawHome.dtos.GuardarMetodoPagoRequest;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.MetodoPagoService;
import com.stripe.exception.StripeException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfil/metodo-pago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;
    private final StripeConfig stripeConfig;

    public MetodoPagoController(MetodoPagoService metodoPagoService, StripeConfig stripeConfig) {
        this.metodoPagoService = metodoPagoService;
        this.stripeConfig = stripeConfig;
    }

    @GetMapping("/anadir")
    public String mostrarFormularioAnadirTarjeta(@AuthenticationPrincipal MyUserDetails userDetails, Model model) throws StripeException {
        Usuario usuario = userDetails.getUsuario();
        String clientSecret = metodoPagoService.crearSetupIntent(usuario);
        model.addAttribute("stripePublicKey", stripeConfig.getPublicKey());
        model.addAttribute("clientSecret", clientSecret);
        model.addAttribute("guardarRequest", new GuardarMetodoPagoRequest());
        return "metodoPagoForm";
    }

    @PostMapping("/guardar")
    public String guardarMetodoPago(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @ModelAttribute("guardarRequest") GuardarMetodoPagoRequest guardarRequest,
            Model model) {
            Usuario usuario = userDetails.getUsuario();
        try {
            metodoPagoService.guardarMetodoPago(guardarRequest.getPaymentMethodId(), usuario, guardarRequest.getAlias());

            model.addAttribute("mensajeExito", "Método de pago guardado correctamente.");
        } catch (Exception e) {
            model.addAttribute("mensajeError", "Error al guardar método de pago: " + e.getMessage());
            return "metodoPagoForm";
        }
        return "redirect:/perfil/informacion";
    }
}

