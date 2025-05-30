package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}