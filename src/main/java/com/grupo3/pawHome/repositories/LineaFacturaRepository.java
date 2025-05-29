package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.LineaFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaFacturaRepository extends JpaRepository<LineaFactura, Integer> {
}