package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Tarifa;
import com.grupo3.pawHome.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/tarifas")
public class AdminTarifController {
    @Autowired
    private TarifaRepository tarifaRepository;

    // Listar tarifas y mostrar formulario (crear o editar)
    @GetMapping
    public String listarTarifas(Model model) {
        model.addAttribute("tarifas", tarifaRepository.findAll());
        model.addAttribute("tarifa", new Tarifa()); // Formulario vacío para crear
        return "adminTarifas";
    }

    // Guardar tarifa (nueva o editada)
    @PostMapping("/guardar")
    public String guardarTarifa(@ModelAttribute("tarifa") Tarifa tarifa) {
        tarifaRepository.save(tarifa);
        return "redirect:/admin/tarifas";
    }

    // Editar tarifa (rellena el formulario con los datos de la tarifa)
    @GetMapping("/editar/{id}")
    public String editarTarifa(@PathVariable Integer id, Model model) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarifa no encontrada con ID: " + id));
        model.addAttribute("tarifa", tarifa); // Formulario relleno
        return "editarAdminTarifas";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarTarifa(@ModelAttribute("tarifa") Tarifa tarifa) {
        tarifaRepository.save(tarifa);
        return "redirect:/admin/tarifas";
    }

    // Eliminar tarifa
    @GetMapping("/eliminar/{id}")
    public String eliminarTarifa(@PathVariable Integer id) {
        tarifaRepository.deleteById(id);
        return "redirect:/admin/tarifas";
    }
}

