package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.AnimalesService;
import com.grupo3.pawHome.services.ApadrinarService;
import com.grupo3.pawHome.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UsuarioController {
    private final AnimalesService animalesService;
    private final UsuarioService usuarioService;
    private final ApadrinarService apadrinarService;

    public UsuarioController(AnimalesService animalesService, UsuarioService usuarioService, ApadrinarService apadrinarService) {
        this.animalesService = animalesService;
        this.usuarioService = usuarioService;
        this.apadrinarService = apadrinarService;
    }

    @GetMapping("/perfil/animales")
    public String mostrarPerfilAnimales(Model model) {

        Optional<Usuario> usuarioOptional = usuarioService.findById(1L);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            List<Apadrinar> apadrinamientos = apadrinarService.findByUsuarioId(1L);

            List<Apadrinar> apadrinamientosInactivos = apadrinamientos.stream()
                    .filter(a -> a.getFechaBaja() != null)
                    .collect(Collectors.toList());

            List<Apadrinar> apadrinamientosActivos = apadrinamientos.stream()
                    .filter(a -> a.getFechaBaja() == null)
                    .collect(Collectors.toList());

            model.addAttribute("usuario", usuario);
            model.addAttribute("apadrinamientosActivos", apadrinamientosActivos);
            model.addAttribute("apadrinamientosInactivos", apadrinamientosInactivos);
        } else {
            model.addAttribute("usuario", null);
            model.addAttribute("apadrinamientosInactivos", List.of());
            model.addAttribute("apadrinamientosActivos", List.of());
        }

        return "perfilUsuarioAnimales";
    }
}
