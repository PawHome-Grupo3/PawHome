package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.MetodoPago;
import com.grupo3.pawHome.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
    List<MetodoPago> findByTipoPago_Nombre(String nombre);

    List<MetodoPago> findAllByUsuario(Usuario usuario);

    MetodoPago findByFingerPrintAndUsuario(String fingerPrint, Usuario usuario);
}
