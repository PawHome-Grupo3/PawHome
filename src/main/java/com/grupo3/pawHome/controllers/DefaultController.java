package com.grupo3.pawHome.controllers;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class DefaultController {

    // Metodo para mostrar pagina de inicio
    @GetMapping("")
    public String mostrarIndex(Model model) {
        model.addAttribute("currentLocale", LocaleContextHolder.getLocale().getDisplayName());
        return "index"; // Carga /templates/index.html
    }

    // Metodo para mostrar la pagina de asesoramiento legal
    @GetMapping("/asesoramientoLegal")
    public String mostrarAsesoramientoLegal()
    {
        return "asesoramientoLegal";
    }

    // Metodo para mostrar la pagina de contacto
    @GetMapping("/contacto")
    public String mostrarContacto()
    {
        return "contacto";
    }

    // Metodo para mostrar la pagina del buzón de sugerencias
    @GetMapping("/buzonsugerencias")
    public String mostrarBuzonSugerencias()
    {
        return "buzonsugerencias";
    }

    // Metodo para mostrar la pagina del horario y el mapa
    @GetMapping("/horariomapa")
    public String mostrarHorarioMapa()
    {
        return "horariomapa";
    }

    @GetMapping("/perfil/puntos")
    public String mostrarPerfilPuntos()
    { return "perfilUsuarioPuntos"; }

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

//    // Metodo para mostrar la página de Reseñas
//    @GetMapping("resenas")
//    public String mostrarResenas()
//    {
//        return "resenas";
//    }

    @GetMapping("/eventos")
    public String mostrarEventos(){
        return "eventos";
    }

    // Metodo para mostrar la página de Colabora
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
    public String mostrarformularioapadrina()
    { return "formularioApadrina"; }

    @GetMapping("/colabora/adopta")
    public String mostrarColaboraAdopta()
    { return "Adopta"; }

    @GetMapping("/colabora/adopta/formularioAdopta")
    public String mostrarformularioadopta()
    { return "formularioAdopta"; }

    @GetMapping("/colabora/paseosolidario")
    public String mostrarColaboraPaseoSolidario()
    { return "PaseoSolidario"; }

    @GetMapping("/colabora/paseosolidario/formularioPS")
    public String mostrarformularioPS()
    { return "formularioPS"; }

    @GetMapping("/finalfeliz")
    public String mostrarFinalesFelices()
    { return "FinalFeliz"; }

    @GetMapping("/registro")
    public String mostrarRegistro()
    { return "registro"; }

    @GetMapping("/loginPawHome")
    public String mostrarLogin()
    { return "login"; }
}




