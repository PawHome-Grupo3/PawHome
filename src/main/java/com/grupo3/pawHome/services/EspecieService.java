package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Especie;
import com.grupo3.pawHome.repositories.EspecieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecieService {
    private final EspecieRepository especieRepository;

    public EspecieService(EspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    public List<Especie> findByRazaId(Integer razaId) { return especieRepository.findByRazaId(razaId); }
}
