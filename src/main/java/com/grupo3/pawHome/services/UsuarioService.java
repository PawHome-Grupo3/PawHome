package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository repository, UsuarioRepository usuarioRepository) { this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return repository.findById(id);
    }

    public void save(Usuario usuario) { usuarioRepository.save(usuario); }
}
