package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.dtos.RegistroDTO;
import com.grupo3.pawHome.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/loginPawHome")
    public String mostrarLoginRegistro(Model model) {
        model.addAttribute("registroDTO", new RegistroDTO());

        return "loginRegistro"; }

    // Procesa el formulario
    @PostMapping("/registro")
    public String procesarFormularioRegistro(@ModelAttribute("registroDTO") @Valid RegistroDTO request,
                                             BindingResult result,
                                             Model model) {
        if (result.hasErrors()) {
            return "loginRegistro"; // vuelve a la vista con errores de validación
        }

        try {
            usuarioService.registrarUsuario(request);
            return "redirect:/auth/login?registroExitoso"; // redirige al login con mensaje
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "loginRegistro";
        }
    }

    @GetMapping("/login")
    public String mostrarRegistroExistoso() {
        return "registroExitoso";
    }
}
