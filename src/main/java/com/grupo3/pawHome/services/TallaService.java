package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Talla;
import com.grupo3.pawHome.repositories.TallaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TallaService {
    private final TallaRepository tallaRepository;

    public TallaService(TallaRepository tallaRepository) {
        this.tallaRepository = tallaRepository;
    }

    public Optional<Talla> findById(int tallaId) { return tallaRepository.findById(tallaId); }

    public Talla save(Talla talla) { return tallaRepository.save(talla); }
}
