package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Apadrinar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApadrinarRepository extends JpaRepository<Apadrinar, Long> {
    List<Apadrinar> findByUsuarioId(Long usuarioId);
}