package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.PerfilDatosService;
import com.grupo3.pawHome.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PerfilDatosController {
    private final UsuarioService usuarioService;
    private final PerfilDatosService perfilDatosService;

    public PerfilDatosController(UsuarioService usuarioService, PerfilDatosService perfilDatosService) {
        this.usuarioService = usuarioService;
        this.perfilDatosService = perfilDatosService;
    }

    // Metodo para mostrar la pagina del horario y el mapa
    @GetMapping("/perfil/{id}")
    public String mostrarPerfil(@PathVariable("id") long id, Model model) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            model.addAttribute("usuario", usuario);
            return "perfilUsuario";
        }else {
            return "redirect:/error";
        }
    }
}
