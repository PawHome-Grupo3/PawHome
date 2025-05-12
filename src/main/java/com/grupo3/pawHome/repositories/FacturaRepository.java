package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}