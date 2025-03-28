package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.EntidadPadre;
import com.atm.buenas_practicas_java.repositories.EntidadPadreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntidadPadreService {

    private final EntidadPadreRepository repository;

    public EntidadPadreService(EntidadPadreRepository repository) {
        this.repository = repository;
    }

    public List<EntidadPadre> findAll() {
        return repository.findAll();
    }

    public Optional<EntidadPadre> findById(Long id) {
        return repository.findById(id);
    }

    public EntidadPadre save(EntidadPadre entidadPadre) {
        return repository.save(entidadPadre);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
