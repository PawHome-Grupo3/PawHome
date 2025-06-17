package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Adopcion;
import com.grupo3.pawHome.services.AdopcionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/adopciones")
public class AdminAdopcionController {

    private final AdopcionService adopcionService;

    public AdminAdopcionController(AdopcionService adopcionService) {
        this.adopcionService = adopcionService;
    }

    @GetMapping
    public String listarAdopciones(Model model) {
        List<Adopcion> adopciones = adopcionService.findAll();
        adopciones.sort(Comparator.comparing(Adopcion::getId));
        model.addAttribute("adopciones", adopciones);
        return "adminAdopciones";
    }


    @PostMapping("/aceptar/{id}")
    public String aceptarAdopcion(@PathVariable Integer id) {
        adopcionService.cambiarEstadoAdopcion(id, true);
        return "redirect:/admin/adopciones";
    }

    @PostMapping("/denegar/{id}")
    public String denegarAdopcion(@PathVariable Integer id) {
        adopcionService.cambiarEstadoAdopcion(id, false);
        return "redirect:/admin/adopciones";
    }
}