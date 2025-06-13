package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Producto;
import com.grupo3.pawHome.entities.Talla;
import com.grupo3.pawHome.repositories.ProductoRepository;
import com.grupo3.pawHome.repositories.TallaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/prodserv")
public class AdminProdServController {
    @Autowired
    private ProductoRepository productoRepository;
    private TallaRepository tallaRepository;

    // Listar productos y servicios y mostrar formulario (crear o editar)
    @GetMapping
    public String listarProdServ(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        model.addAttribute("producto", new Producto()); // Formulario vacío para crear
//        model.addAttribute("tallas", tallaRepository.findAll());
//        model.addAttribute("talla", new Talla()); // Formulario vacío para crear talla
        return "adminProdServ";
    }

    // Guardar producto (nuevo o editado)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        productoRepository.save(producto);
        return "redirect:/admin/prodserv";
    }

//    // Guardar talla (nueva o editada)
//    @PostMapping("/guardar")
//    public String guardarTalla(@ModelAttribute("talla") Talla talla) {
//        tallaRepository.save(talla);
//        return "redirect:/admin/prodserv";
//    }

    // Editar productos y servicios (rellena el formulario con los datos del producto o de la talla)
    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto o servicio no encontrado con ID: " + id));
        model.addAttribute("producto", producto); // Formulario relleno
        return "editarAdminProdServ";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProducto(@ModelAttribute("producto") Producto producto) {
        productoRepository.save(producto);
        return "redirect:/admin/prodserv";
    }

    // Eliminar producto o servicio
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productoRepository.deleteById(id);
        return "redirect:/admin/prodserv";
    }

//    // Eliminar talla
//    @GetMapping("/eliminar/{id}")
//    public String eliminarTalla(@PathVariable Integer id) {
//        tallaRepository.deleteById(id);
//        return "redirect:/admin/prodserv";
//    }
}
