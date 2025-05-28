package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Categoria;
import com.grupo3.pawHome.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoria(Categoria categoria);
}