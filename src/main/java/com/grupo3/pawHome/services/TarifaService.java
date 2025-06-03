package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Tarifa;
import com.grupo3.pawHome.repositories.TarifaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TarifaService {
    private final TarifaRepository tarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    public Optional<Tarifa> findTopByProductoIdOrderByFechaDesdeDesc(int id) {
        return tarifaRepository.findTopByProductoIdOrderByFechaDesdeDesc(id);
    }
}
