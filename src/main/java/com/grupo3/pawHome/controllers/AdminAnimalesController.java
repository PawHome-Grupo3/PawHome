package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Animal;
import com.grupo3.pawHome.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/animales")
public class AdminAnimalesController {
    @Autowired
    private AnimalRepository animalRepository;

    // Listar animales y mostrar formulario (crear o editar)
    @GetMapping
    public String listarAnimales(Model model) {
        model.addAttribute("animales", animalRepository.findAll());
        model.addAttribute("animal", new Animal()); // Formulario vacío para crear
        return "adminAnimales";
    }

    // Guardar animal (nuevo o editado)
    @PostMapping("/guardar")
    public String guardarAnimal(@ModelAttribute("animal") Animal animal) {
        animalRepository.save(animal);
        return "redirect:/admin/animales";
    }

    // Editar animal (rellena el formulario con los datos del animal)
    @GetMapping("/editar/{id}")
    public String editarAnimal(@PathVariable Integer id, Model model) {
        model.addAttribute("animales", animalRepository.findAll());
        model.addAttribute("animal", animalRepository.findById(id).orElse(new Animal())); // Formulario relleno
        return "adminAnimales";
    }

    // Eliminar animal
    @GetMapping("/eliminar/{id}")
    public String eliminarAnimal(@PathVariable Integer id) {
        animalRepository.deleteById(id);
        return "redirect:/admin/animales";
    }
}