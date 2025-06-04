package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.dtos.PerfilDatosDTO;
import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.ApadrinarService;
import com.grupo3.pawHome.services.PerfilDatosService;
import com.grupo3.pawHome.services.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Controller
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ApadrinarService apadrinarService;
    private final PerfilDatosService perfilDatosService;

    public UsuarioController(UsuarioService usuarioService, ApadrinarService apadrinarService, PerfilDatosService perfilDatosService) {
        this.usuarioService = usuarioService;
        this.apadrinarService = apadrinarService;
        this.perfilDatosService = perfilDatosService;
    }

    @GetMapping("/perfil/informacion")
    public String mostrarPerfil(@AuthenticationPrincipal Usuario usuario, Model model) {
        System.out.println("Usuario autenticado: " + usuario);
        model.addAttribute("usuario", usuario);
        return "perfilUsuario";

    }

    @GetMapping("/perfil/editar")
    public String mostrarFormulario(Model model) {
        Usuario usuario = usuarioService.findById(3).orElseThrow();

        PerfilDatosDTO dto = new PerfilDatosDTO();

        if (usuario.getPerfilDatos() != null) {
            PerfilDatos perfil = usuario.getPerfilDatos();
            dto.setNombre(perfil.getNombre());
            dto.setApellidos(perfil.getApellidos());
            dto.setEdad(perfil.getEdad());
            dto.setDni(perfil.getDni());
            dto.setDireccion(perfil.getDireccion());
            dto.setCiudad(perfil.getCiudad());
            dto.setCp(perfil.getCp());
            dto.setTelefono1(perfil.getTelefono1());
            dto.setTelefono2(perfil.getTelefono2());
            dto.setTelefono3(perfil.getTelefono3());
        }

        model.addAttribute("perfilDTO", dto);
        return "perfilUsuarioEditar"; // Nombre del HTML
    }

    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@ModelAttribute("perfilDTO") PerfilDatosDTO dto) {
        Usuario usuario = usuarioService.findById(3).orElseThrow();

        PerfilDatos perfil;

        if (usuario.getPerfilDatos() != null) {
            perfil = usuario.getPerfilDatos(); // editar
        } else {
            perfil = new PerfilDatos(); // crear
            perfil.setUsuario(usuario);
            perfil.setId(usuario.getId()); // importante por @MapsId
        }

        perfil.setNombre(dto.getNombre());
        perfil.setApellidos(dto.getApellidos());
        perfil.setEdad(dto.getEdad());
        perfil.setDni(dto.getDni());
        perfil.setDireccion(dto.getDireccion());
        perfil.setCiudad(dto.getCiudad());
        perfil.setCp(dto.getCp());
        perfil.setTelefono1(dto.getTelefono1());
        perfil.setTelefono2(dto.getTelefono2());
        perfil.setTelefono3(dto.getTelefono3());

        perfilDatosService.save(perfil);

        return "redirect:/perfil/editar";
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
}
