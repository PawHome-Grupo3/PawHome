package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Animales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalesRepository extends JpaRepository<Animales, Long> {
}