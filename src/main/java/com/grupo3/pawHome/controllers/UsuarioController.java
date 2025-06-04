package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.ApadrinarService;
import com.grupo3.pawHome.services.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Controller
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ApadrinarService apadrinarService;

    public UsuarioController(UsuarioService usuarioService, ApadrinarService apadrinarService) {
        this.usuarioService = usuarioService;
        this.apadrinarService = apadrinarService;
    }

    @GetMapping("/perfil/informacion")
    public String mostrarPerfil(@AuthenticationPrincipal Usuario usuario, Model model) {
        System.out.println("Usuario autenticado: " + usuario);
        model.addAttribute("usuario", usuario);
        return "perfilUsuario";
    }

    @GetMapping("/perfil/apadrinamientos")
    public String mostrarPerfilApadrinamientos(Model model) {

        Optional<Usuario> usuarioOptional = usuarioService.findById(1);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Set<Apadrinar> apadrinamientosActivos = apadrinarService.apadrinamientosActivosPorUsuario(usuario.getId());
            Set<Apadrinar> apadrinamientosInactivos = apadrinarService.apadrinamientosInactivosPorUsuario(usuario.getId());

            if (!apadrinamientosActivos.isEmpty()) model.addAttribute("apadrinamientosActivos", apadrinamientosActivos);
            else model.addAttribute("apadrinamientosActivos", null);

            if (!apadrinamientosInactivos.isEmpty()) model.addAttribute("apadrinamientosInactivos", apadrinamientosInactivos);
            else model.addAttribute("apadrinamientosInactivos", null);

            model.addAttribute("usuario", usuario);

        } else {
            model.addAttribute("usuario", null);
        }

        return "perfilUsuarioAnimales";
    }

    @PostMapping("/perfil/apadrinamientos/finalizar")
    public String finalizarApadrinamiento(@RequestParam("apadrinamientoId") int apadrinamientoId) {
        Optional<Apadrinar> apadrinarOpt = apadrinarService.findById(apadrinamientoId);

        if (apadrinarOpt.isPresent()) {
            Apadrinar apadrinamiento = apadrinarOpt.get();
            apadrinamiento.setFechaBaja(LocalDate.now());
            apadrinarService.save(apadrinamiento);
        }

        return "redirect:/perfil/apadrinamientos";
    }

    @PostMapping("/perfil/apadrinamientos/reactivar")
    public String reactivarApadrinamiento(@RequestParam("apadrinamientoId") int apadrinamientoId) {
        Optional<Apadrinar> apadrinarOpt = apadrinarService.findById(apadrinamientoId);

        if (apadrinarOpt.isPresent()) {
            Apadrinar apadrinamiento = apadrinarOpt.get();
            apadrinamiento.setFechaBaja(null);
            apadrinamiento.setFechaInicio(LocalDate.now());
            apadrinamiento.setFechaRenovacion(LocalDate.now().plusMonths(1));
            apadrinarService.save(apadrinamiento);
        }

        return "redirect:/perfil/apadrinamientos";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Retorna la vista login.html desde /templates
    }
}
