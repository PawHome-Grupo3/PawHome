package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Animal;
import com.grupo3.pawHome.entities.Especie;
import com.grupo3.pawHome.entities.Raza;
import com.grupo3.pawHome.repositories.AnimalRepository;
import com.grupo3.pawHome.repositories.EspecieRepository;
import com.grupo3.pawHome.repositories.RazaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/animales")
public class AdminAnimalesController {

    private final AnimalRepository animalRepository;
    private final RazaRepository razaRepository;
    private final EspecieRepository especieRepository;

    public AdminAnimalesController(AnimalRepository animalRepository, RazaRepository razaRepository, EspecieRepository especieRepository) {
        this.animalRepository = animalRepository;
        this.razaRepository = razaRepository;
        this.especieRepository = especieRepository;
    }

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
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Animal no encontrado con ID: " + id));

        model.addAttribute("animal", animal);
        model.addAttribute("razas", razaRepository.findAll());
        return "editarAdminAnimales";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarAnimal(@ModelAttribute Animal animal, @PathVariable Integer id) {
        Raza raza = razaRepository.findById(animal.getRaza().getId())
                .orElseThrow(() -> new IllegalArgumentException("Raza no encontrada"));

        animal.setRaza(raza);
        animalRepository.save(animal);
        return "redirect:/admin/animales";
    }

    // Eliminar animal
    @GetMapping("/eliminar/{id}")
    public String eliminarAnimal(@PathVariable Integer id) {
        animalRepository.deleteById(id);
        return "redirect:/admin/animales";
    }
}