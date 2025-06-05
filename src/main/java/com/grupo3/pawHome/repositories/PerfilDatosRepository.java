package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.PerfilDatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilDatosRepository extends JpaRepository<PerfilDatos, Integer> {
}
