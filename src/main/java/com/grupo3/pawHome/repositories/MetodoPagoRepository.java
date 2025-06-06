package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
    List<MetodoPago> findByTipoPago_Nombre(String nombre);
}
