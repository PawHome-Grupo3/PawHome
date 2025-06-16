package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Adopcion;
import com.grupo3.pawHome.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdopcionRepository extends JpaRepository<Adopcion, Integer> {
    List<Adopcion> findAllByUsuario(Usuario usuario);
}