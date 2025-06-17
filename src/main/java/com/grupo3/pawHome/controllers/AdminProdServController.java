package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Categoria;
import com.grupo3.pawHome.entities.Producto;
import com.grupo3.pawHome.entities.Talla;
import com.grupo3.pawHome.repositories.CategoriaRepository;
import com.grupo3.pawHome.repositories.ProductoRepository;
import com.grupo3.pawHome.repositories.TallaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/prodserv")
public class AdminProdServController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public AdminProdServController(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Listar productos y servicios y mostrar formulario (crear o editar)
    @GetMapping
    public String listarProdServ(Model model) {
        List<Producto> productos = productoRepository.findAll();
        productos.sort(Comparator.comparing(Producto::getId));
        model.addAttribute("productos", productoRepository.findAll());
        model.addAttribute("producto", new Producto()); // Formulario vacío para crear
        return "adminProdServ";
    }

    // Guardar producto (nuevo o editado)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        productoRepository.save(producto);
        return "redirect:/admin/prodserv";
    }


    // Editar productos y servicios (rellena el formulario con los datos del producto o de la talla)
    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto o servicio no encontrado con ID: " + id));
        model.addAttribute("producto", producto); // Formulario relleno
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "editarAdminProdServ";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProducto(@ModelAttribute("producto") Producto producto) {
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        producto.setCategoria(categoria);
        productoRepository.save(producto);
        return "redirect:/admin/prodserv";
    }

    // Eliminar producto o servicio
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productoRepository.deleteById(id);
        return "redirect:/admin/prodserv";
    }

}
