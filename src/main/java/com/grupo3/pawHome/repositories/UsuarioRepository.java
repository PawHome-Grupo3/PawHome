package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}