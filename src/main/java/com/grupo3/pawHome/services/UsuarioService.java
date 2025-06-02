package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) { this.repository = repository; }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Optional<Usuario> findById(long id) {
        return repository.findById(id);
    }
}