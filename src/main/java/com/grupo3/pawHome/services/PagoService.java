package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Pago;
import com.grupo3.pawHome.repositories.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public Pago save(Pago pago) { return pagoRepository.save(pago); }

    public Optional<Pago> findById(int usuarioId) { return pagoRepository.findById(usuarioId); }
}
