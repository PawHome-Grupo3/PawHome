package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    List<Factura> findByUsuarioId(Integer usuarioId);

    // Verifica si existen facturas asociadas al ID del usuario
    boolean existsByUsuario_Id(Long usuarioId);
}