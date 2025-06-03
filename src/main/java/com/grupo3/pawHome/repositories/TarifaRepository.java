package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {
    Optional<Tarifa> findTopByProductoIdOrderByFechaDesdeDesc(int productoId);
}