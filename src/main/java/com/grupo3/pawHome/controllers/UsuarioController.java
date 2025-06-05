package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.dtos.CountryDTO;
import com.grupo3.pawHome.dtos.PerfilDatosDTO;
import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.ApadrinarService;
import com.grupo3.pawHome.services.LocationService;
import com.grupo3.pawHome.services.PerfilDatosService;
import com.grupo3.pawHome.services.UsuarioService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ApadrinarService apadrinarService;
    private final PerfilDatosService perfilDatosService;
    private final LocationService locationService;


    public UsuarioController(UsuarioService usuarioService, ApadrinarService apadrinarService, PerfilDatosService perfilDatosService, LocationService locationService) {
        this.usuarioService = usuarioService;
        this.apadrinarService = apadrinarService;
        this.perfilDatosService = perfilDatosService;
        this.locationService = locationService;
    }

    @GetMapping("/perfil/informacion")
    public String mostrarPerfil(@AuthenticationPrincipal Usuario usuario, Model model) {
        model.addAttribute("usuario", usuario);
        return "perfilUsuario";
    }

    @GetMapping("/perfil/editar")
    public String mostrarFormulario(@AuthenticationPrincipal Usuario usuario,
                                    @RequestParam(value = "pais", required = false) String paisSeleccionado,
                                    Model model) throws Exception {
        System.out.println("======== INICIO mostrarFormulario ========");

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
            dto.setPais(perfil.getPais());
        }

        model.addAttribute("perfilDTO", dto);

        // Obtener todos los países
        List<CountryDTO> paises = locationService.getAllCountries();
        model.addAttribute("paises", paises);
        System.out.println("Cargados países: " + paises.size());

        // Determinar el país (del parámetro o del perfil)
        String pais = paisSeleccionado != null ? paisSeleccionado : dto.getPais();
        model.addAttribute("paisSeleccionado", pais);

        // Obtener ciudades si hay país seleccionado
        if (pais != null && !pais.isBlank()) {
            try {
                List<String> ciudades = locationService.getCitiesForCountry(pais);
                model.addAttribute("ciudades", ciudades);
                System.out.println("País seleccionado: " + pais + " -> ciudades cargadas: " + ciudades.size());
            } catch (Exception e) {
                System.out.println("Error obteniendo ciudades para país: " + pais);
                e.printStackTrace();
                model.addAttribute("ciudades", Collections.emptyList());
            }
        } else {
            System.out.println("No se seleccionó país, lista de ciudades vacía");
            model.addAttribute("ciudades", Collections.emptyList());
        }

        System.out.println("======== FIN mostrarFormulario ========");
        return "perfilUsuarioEditar";
    }

    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@AuthenticationPrincipal Usuario authUsuario, @ModelAttribute("perfilDTO") PerfilDatosDTO dto) {

        Usuario usuario = usuarioService.findById(authUsuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        Optional<Usuario> userOpt = usuarioService.findById(usuario.getId());

        PerfilDatos perfil = new PerfilDatos();

        if (userOpt.isPresent()) {
            Usuario managedUser = userOpt.get();
            perfil = managedUser.getPerfilDatos();

            if (perfil == null) {
                perfil = new PerfilDatos();
                perfil.setUsuario(managedUser); // importante
            }

            perfil.setNombre(dto.getNombre());
            perfil.setApellidos(dto.getApellidos());
            perfil.setEdad(dto.getEdad());
            perfil.setDni(dto.getDni());
            perfil.setDireccion(dto.getDireccion());
            perfil.setPais(dto.getPais());
            perfil.setCiudad(dto.getCiudad());
            perfil.setCp(dto.getCp());
            perfil.setTelefono1(dto.getTelefono1());
            perfil.setTelefono2(dto.getTelefono2());
            perfil.setTelefono3(dto.getTelefono3());
            managedUser.setPerfilDatos(perfil);

            usuarioService.save(managedUser);

            Usuario usuarioActualizado = usuarioService.findById(authUsuario.getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Authentication newAuth = new UsernamePasswordAuthenticationToken(usuarioActualizado, usuarioActualizado.getPassword(), usuarioActualizado.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

        return "redirect:/perfil/editar";
    }

    @GetMapping("/perfil/apadrinamientos")
    public String mostrarPerfilApadrinamientos(@AuthenticationPrincipal Usuario usuario, Model model) {

        if (usuario != null) {
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

        return "perfilUsuarioApadrinamientos";
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
        return "login";
    }

}
