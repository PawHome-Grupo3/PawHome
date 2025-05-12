package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Animales;
import com.grupo3.pawHome.services.AnimalesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AnimalesController {
    private final AnimalesService animalesService;

    public AnimalesController(AnimalesService animalesService) {
        this.animalesService = animalesService;
    }

    @GetMapping("/animales")
    public String mostrarAnimales(Model model) {
        List<Animales> animales = animalesService.findAll();
        model.addAttribute("animales", animales);
        return "animales"; // Nombre del archivo HTML en src/main/resources/templates/
    }

}
