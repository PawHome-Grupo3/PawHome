package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {
    Optional<Tarifa> findTopByProductoIdOrderByFechaDesdeDesc(int productoId);
}