package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.MetodoDonacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetodoDonacionRepository extends JpaRepository<MetodoDonacion, Long> {
    List<MetodoDonacion> findByTipo(String tipo);
}