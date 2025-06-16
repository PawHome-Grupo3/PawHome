package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Adopcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdopcionRepository extends JpaRepository<Adopcion, Integer> {
}