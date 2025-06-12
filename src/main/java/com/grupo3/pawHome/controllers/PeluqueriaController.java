package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.ProductoService;
import com.grupo3.pawHome.services.StripeService;
import com.grupo3.pawHome.services.UsuarioService;
import com.grupo3.pawHome.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String mostrarGuarderia(@AuthenticationPrincipal Usuario usuario, Model model) {
        model.addAttribute("usuario", usuario);
        return "peluqueria";
    }
}
