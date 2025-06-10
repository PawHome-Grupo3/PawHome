package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuarderiaController {
    private final UsuarioService usuarioService;

    public GuarderiaController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Metodo para mostrar la pagina de la guarderia
    @GetMapping("/guarderia")
    public String mostrarGuarderia(@AuthenticationPrincipal Usuario usuario, Model model) {
        model.addAttribute("usuario", usuario);
        return "guarderia";
    }
}
