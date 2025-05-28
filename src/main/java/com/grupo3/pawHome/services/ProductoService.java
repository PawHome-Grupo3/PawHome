package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Categoria;
import com.grupo3.pawHome.entities.Producto;
import com.grupo3.pawHome.repositories.CategoriaRepository;
import com.grupo3.pawHome.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Producto> findAll() { return productoRepository.findAll(); }
    public List<Producto> findByCategoria(Categoria categoria) { return productoRepository.findByCategoria(categoria);}
}
