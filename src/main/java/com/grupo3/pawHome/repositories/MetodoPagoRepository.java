package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    List<MetodoPago> findByTipo(String tipo);
}