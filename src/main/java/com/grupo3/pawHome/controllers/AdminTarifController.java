package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Producto;
import com.grupo3.pawHome.entities.Tarifa;
import com.grupo3.pawHome.repositories.ProductoRepository;
import com.grupo3.pawHome.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/tarifas")
public class AdminTarifController {

    private final TarifaRepository tarifaRepository;
    private final ProductoRepository productoRepository;

    public AdminTarifController(TarifaRepository tarifaRepository, ProductoRepository productoRepository) {
        this.tarifaRepository = tarifaRepository;
        this.productoRepository = productoRepository;
    }

    // Listar tarifas
    @GetMapping
    public String listarTarifas(Model model) {
        List<Tarifa> tarifas = tarifaRepository.findAll();
        tarifas.sort(Comparator.comparing(Tarifa::getId));
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

    // Editar tarifa
    @GetMapping("/editar/{id}")
    public String editarTarifa(@PathVariable Integer id, Model model) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarifa no encontrada con ID: " + id));
        model.addAttribute("tarifa", tarifa); // Formulario relleno
        model.addAttribute("productos", productoRepository.findAll());
        return "editarAdminTarifas";
    }

    // Actualizar tarifa
    @PostMapping("/actualizar/{id}")
    public String actualizarTarifa(@ModelAttribute("tarifa") Tarifa tarifa) {
        Producto producto = productoRepository.findById(tarifa.getProducto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        tarifa.setProducto(producto);
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

