package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.dtos.CountryDTO;
import com.grupo3.pawHome.dtos.FacturaDTO;
import com.grupo3.pawHome.dtos.PerfilDatosDTO;
import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.entities.MetodoPago;
import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.FacturaRepository;
import com.grupo3.pawHome.services.*;
import com.grupo3.pawHome.util.PaisUtils;
import com.grupo3.pawHome.util.SecurityUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final LocationService locationService;
    private final MetodoPagoService metodoPagoService;
    private final FacturaService facturaService;
    private final FacturaRepository facturaRepository;
    private final PaisUtils paisUtils;
    private final SecurityUtil securityUtil;
    private final StripeService stripeService;

    public UsuarioController(UsuarioService usuarioService,
                             ApadrinarService apadrinarService,
                             LocationService locationService,
                             MetodoPagoService metodoPagoService, PaisUtils paisUtils, FacturaService facturaService, FacturaRepository facturaRepository, SecurityUtil securityUtil, StripeService stripeService) {
        this.usuarioService = usuarioService;
        this.apadrinarService = apadrinarService;
        this.locationService = locationService;
        this.metodoPagoService = metodoPagoService;
        this.paisUtils = paisUtils;
        this.facturaService = facturaService;
        this.facturaRepository = facturaRepository;
        this.securityUtil = securityUtil;
        this.stripeService = stripeService;
    }

    @GetMapping("/perfil/informacion")
    public String mostrarPerfil(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {

        Usuario usuario = userDetails.getUsuario();

        model.addAttribute("usuario", usuario);
        List<MetodoPago> metodoPagos = metodoPagoService.findAllByUsuario(usuario);

        if(metodoPagos.isEmpty()){
            model.addAttribute("metodosPago", null);
        }
        else{
            model.addAttribute("metodosPago", metodoPagos);
        }

        return "perfilUsuario";
    }

    @GetMapping("/perfil/editar")
    public String mostrarFormulario(@AuthenticationPrincipal MyUserDetails userDetails,
                                    @RequestParam(value = "paisSeleccionado", required = false) String paisSeleccionado,
                                    Model model) throws Exception {
        Usuario usuario = userDetails.getUsuario();

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

            String codigoPais = paisUtils.obtenerCodigoDesdeNombre(perfil.getPais());
            String nombrePais = paisUtils.obtenerNombrePais(codigoPais);
            dto.setCodigoPais(codigoPais);
            dto.setNombrePais(nombrePais);

            model.addAttribute("ciudadSeleccionada", dto.getCiudad());
        }

        model.addAttribute("perfilDTO", dto);

        List<CountryDTO> paises = locationService.getAllCountries();
        model.addAttribute("paises", paises);

        // Prioriza el país del request param, si no, usa el del perfil
        String paisCode = (paisSeleccionado != null && !paisSeleccionado.isBlank())
                ? paisSeleccionado
                : dto.getCodigoPais();

        model.addAttribute("paisSeleccionado", paisCode);

        if (paisCode != null && !paisCode.isBlank()) {
            try {
                List<String> ciudades = locationService.getCitiesForCountry(paisCode);
                model.addAttribute("ciudades", ciudades);
            } catch (Exception e) {
                model.addAttribute("ciudades", Collections.emptyList());
            }
        } else {
            model.addAttribute("ciudades", Collections.emptyList());
        }

        return "perfilUsuarioEditar";
    }

    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@AuthenticationPrincipal MyUserDetails userDetails,
                                @ModelAttribute("perfilDTO") PerfilDatosDTO dto) {

        Usuario authUsuario = userDetails.getUsuario();

        Usuario usuario = usuarioService.findById(authUsuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        PerfilDatos perfil = usuario.getPerfilDatos();

        if (perfil == null) {
            perfil = new PerfilDatos();
            perfil.setUsuario(usuario);
        }

        perfil.setNombre(dto.getNombre());
        perfil.setApellidos(dto.getApellidos());
        perfil.setEdad(dto.getEdad());
        perfil.setDni(dto.getDni());
        perfil.setDireccion(dto.getDireccion());
        perfil.setPais(dto.getCodigoPais());
        perfil.setCiudad(dto.getCiudad());
        perfil.setCp(dto.getCp());
        perfil.setTelefono1(dto.getTelefono1());
        perfil.setTelefono2(dto.getTelefono2());
        perfil.setTelefono3(dto.getTelefono3());

        // Asociar el perfil al usuario y guardar y actualizarlo
        usuario.setPerfilDatos(perfil);
        usuarioService.save(usuario);
        securityUtil.updateAuthenticatedUser(usuario);

        return "redirect:/perfil/editar";
    }

    @GetMapping("/perfil/facturas")
    public String mostrarFacturas(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

        Usuario usuario = userDetails.getUsuario();
        boolean tieneFacturas = facturaRepository.existsByUsuario_Id(Long.valueOf(usuario.getId()));

        if(tieneFacturas) {

            List<FacturaDTO> facturas = facturaService.obtenerFacturasPorUsuario(usuario.getId());

            model.addAttribute("facturas", facturas);
        }else{
            model.addAttribute("facturas", null);
        }

        return "perfilUsuarioFacturas"; // la vista HTML donde tienes la tabla
    }

    @GetMapping("/perfil/apadrinamientos")
    public String mostrarPerfilApadrinamientos(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {

        Usuario usuario = userDetails.getUsuario();

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

            // Cancelar en Stripe
            String stripeSubscriptionId = apadrinamiento.getStripeSubscriptionId();
            if (stripeSubscriptionId != null && !stripeSubscriptionId.isEmpty()) {
                boolean cancelado = stripeService.cancelarSuscripcion(stripeSubscriptionId);
                if (!cancelado) {
                    // Opcional: manejar errores de cancelación
                    // Redireccionar a una página de error o mostrar un mensaje
                    return "redirect:/perfil/apadrinamientos?errorStripe";
                }
            }

            // Marcar como inactivo en la BD
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
        return "loginRegistro";
    }

}
