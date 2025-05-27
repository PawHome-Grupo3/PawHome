package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.ApadrinarRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApadrinarService {
    private final ApadrinarRepository repository;

    public ApadrinarService(ApadrinarRepository repository) { this.repository = repository; }

    public List<Apadrinar> findAll() {
        return repository.findAll();
    }

    public Optional<Apadrinar> findById(Long id) { return repository.findById(id); }

    public List<Apadrinar> findByUsuarioId(long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
