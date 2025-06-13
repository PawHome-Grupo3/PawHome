package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Raza;
import com.grupo3.pawHome.repositories.RazaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RazaService {
    private final RazaRepository razaRepository;

    public RazaService(RazaRepository razaRepository) {
        this.razaRepository = razaRepository;
    }

    public List<Raza> findAll() { return razaRepository.findAll(); }
}
