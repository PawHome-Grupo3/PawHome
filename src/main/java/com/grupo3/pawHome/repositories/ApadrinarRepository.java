package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApadrinarRepository extends JpaRepository<Apadrinar, Long> {
    List<Apadrinar> findByUsuarioId(Long usuarioId);
}