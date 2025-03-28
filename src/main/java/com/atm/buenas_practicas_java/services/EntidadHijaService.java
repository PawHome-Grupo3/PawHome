package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.repositories.EntidadHijaRepository;
import com.atm.buenas_practicas_java.entities.EntidadHija;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntidadHijaService {

    private final EntidadHijaRepository repository;

    public EntidadHijaService(EntidadHijaRepository repository) {
        this.repository = repository;
    }

    public List<EntidadHija> findAll() {
        return repository.findAll();
    }

    public Optional<EntidadHija> findById(Long id) {
        return repository.findById(id);
    }

    public EntidadHija save(EntidadHija entidadHija) {
        return repository.save(entidadHija);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
