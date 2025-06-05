package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Categoria;
import com.grupo3.pawHome.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoria(Categoria categoria);
}