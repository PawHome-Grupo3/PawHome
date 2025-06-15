package com.grupo3.pawHome.controllers;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class DefaultController {

    @GetMapping("")
    public String mostrarIndex(Model model) {
        model.addAttribute("currentLocale", LocaleContextHolder.getLocale().getDisplayName());
        return "index";
    }

    @GetMapping("/asesoramientoLegal")
    public String mostrarAsesoramientoLegal()
    {
        return "asesoramientoLegal";
    }

    @GetMapping("/contacto")
    public String mostrarContacto()
    {
        return "contacto";
    }

    @GetMapping("/buzonsugerencias")
    public String mostrarBuzonSugerencias()
    {
        return "buzonsugerencias";
    }

    @GetMapping("/horariomapa")
    public String mostrarHorarioMapa()
    {
        return "horariomapa";
    }

    @GetMapping("/perfil/puntos")
    public String mostrarPerfilPuntos() { return "perfilUsuarioPuntos"; }

    @GetMapping("/perfil/adopciones")
    public String mostrarPerfilAdopciones()
    {
        return "perfilUsuarioAdopciones";
    }

    @GetMapping("/perfil/donaciones")
    public String mostrarPerfilDonaciones()
    {
        return "perfilUsuarioDonaciones";
    }

    @GetMapping("/perfil/citas")
    public String mostrarPerfilCitas()
    {
        return "perfilUsuarioCitas";
    }

    @GetMapping("/politica-privacidad")
    public String mostrarPoliticaPrivacidad(){ return "politicaPrivacidad"; }

    @GetMapping("/politica-cookies")
    public String mostrarPoliticaCookies(){ return "politicaPrivacidad"; }

    @GetMapping("/eventos")
    public String mostrarEventos(){
        return "eventos";
    }

    @GetMapping("/colabora")
    public String mostrarColabora()
    {
        return "Colabora";
    }

    @GetMapping("/colabora/dona/donarBizum")
    public String mostrarColaboraDonaDonarBizum()
    {
        return "donarBizum";
    }

    @GetMapping("/colabora/apadrina")
    public String mostrarColaboraApadrina()
    {
        return "Apadrina";
    }

    @GetMapping("/colabora/Apadrina/formularioApadrina")
    public String mostrarformularioapadrina() { return "formularioApadrina"; }

    @GetMapping("/colabora/adopta")
    public String mostrarColaboraAdopta() { return "Adopta"; }

    @GetMapping("/colabora/paseosolidario")
    public String mostrarColaboraPaseoSolidario() { return "PaseoSolidario"; }

    @GetMapping("/colabora/paseosolidario/formularioPS")
    public String mostrarformularioPS() { return "formularioPS"; }

    @GetMapping("/finalfeliz")
    public String mostrarFinalesFelices() { return "FinalFeliz"; }

    @GetMapping("/loginPawHome")
    public String mostrarLoginRegistro() { return "loginRegistro"; }
}




