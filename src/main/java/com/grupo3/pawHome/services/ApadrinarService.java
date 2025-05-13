package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.repositories.ApadrinarRepository;

import java.util.List;
import java.util.Optional;

public class ApadrinarService {
    private final ApadrinarRepository repository;

    public ApadrinarService(ApadrinarRepository repository) { this.repository = repository; }

    public List<Apadrinar> findAll() {
        return repository.findAll();
    }

    public Optional<Apadrinar> findById(Long id) { return repository.findById(id); }
}
