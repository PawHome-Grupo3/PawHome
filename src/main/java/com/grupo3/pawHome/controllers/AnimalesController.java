package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Animales;
import com.grupo3.pawHome.services.AnimalesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class AnimalesController {
    private final AnimalesService animalesService;

    public AnimalesController(AnimalesService animalesService) {
        this.animalesService = animalesService;
    }

    @GetMapping("/nuestrosAnimales")
    public String mostrarNuestrosAnimales(Model model) {
        List<Animales> animales = animalesService.findAll();
        model.addAttribute("animales", animales);
        return "nuestrosAnimales";
    }

    @GetMapping("/adopta/{id}")
    public String mostrarPaginaDonacion(@PathVariable("id") Long id, Model model) {
        Optional<Animales> animalOpt = animalesService.findById(id); // o animalRepository.findById(id)

        if (animalOpt.isPresent()) {
            Animales animal = animalOpt.get();
            model.addAttribute("animal", animal);
            return "compruebaAdoptaId"; // nombre de la vista Thymeleaf (por ejemplo: templates/dona.html)
        } else {
            return "redirect:/error"; // o cualquier página de error
        }
    }

}
