package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Animales;
import com.grupo3.pawHome.repositories.AnimalesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalesService {
    private final AnimalesRepository repository;

    public AnimalesService(AnimalesRepository repository) {
        this.repository = repository;
    }

    public List<Animales> findAll() {
        return repository.findAll();
    }
}
