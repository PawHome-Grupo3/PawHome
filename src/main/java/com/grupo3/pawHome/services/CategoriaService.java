package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Categoria;
import com.grupo3.pawHome.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> findAll() { return categoriaRepository.findAll(); }

    public List<Categoria> findByNombreContainingIgnoreCase(String tienda) {
        return categoriaRepository.findByNombreContainingIgnoreCase(tienda);
    }
}
