package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Categoria;
import com.grupo3.pawHome.entities.Producto;
import com.grupo3.pawHome.entities.Tarifa;
import com.grupo3.pawHome.services.CategoriaService;
import com.grupo3.pawHome.services.ProductoService;
import com.grupo3.pawHome.services.TarifaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductoController {
    private final CategoriaService categoriaService;
    private final ProductoService productoService;
    private final TarifaService tarifaService;

    public ProductoController(CategoriaService categoriaService, ProductoService productoService, TarifaService tarifaService) {
        this.categoriaService = categoriaService;
        this.productoService = productoService;
        this.tarifaService = tarifaService;
    }

    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
        List<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        return "tienda";
    }

    @GetMapping("/tienda/producto/{id}")
    public String mostrarProducto(@PathVariable("id") int id, Model model) {

        Optional<Producto> productoOpt = productoService.findById(id);

        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            Optional<Tarifa> tarifaActualOpt = tarifaService.findTopByProductoIdOrderByFechaDesdeDesc(id);
            model.addAttribute("producto", producto);
            tarifaActualOpt.ifPresent(tarifa -> model.addAttribute("tarifa", tarifa));
            return "detalleProducto"; // nombre de la vista Thymeleaf (por ejemplo: templates/dona.html)
        } else {
            return "redirect:/error"; // o cualquier página de error
        }
    }
}
