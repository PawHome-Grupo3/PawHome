package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.TipoPago;
import com.grupo3.pawHome.repositories.TipoPagoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TipoPagoService {
    private final TipoPagoRepository tipoPagoRepository;

    public TipoPagoService(TipoPagoRepository tipoPagoRepository) {
        this.tipoPagoRepository = tipoPagoRepository;
    }

    public Optional<TipoPago> findByNombreContains(String tarjetaCredito) {
        return tipoPagoRepository.findByNombreContains(tarjetaCredito);
    }
}
