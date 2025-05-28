package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Categoria;
import com.grupo3.pawHome.services.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductoController {
    private final CategoriaService categoriaService;

    public ProductoController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
        List<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        return "tienda";
    }
}
