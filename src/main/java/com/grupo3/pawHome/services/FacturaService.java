package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Factura;
import com.grupo3.pawHome.repositories.FacturaRepository;
import org.springframework.stereotype.Service;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public Factura save(Factura factura) { return facturaRepository.save(factura); }
}
