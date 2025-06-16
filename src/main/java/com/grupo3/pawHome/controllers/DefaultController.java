package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Animal;
import com.grupo3.pawHome.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/finalfeliz")
    public String mostrarFinalesFelices() { return "FinalFeliz"; }

    @GetMapping("/nosotros")
    public String mostrarNosotros() {
        return "nosotros";
    }
}




