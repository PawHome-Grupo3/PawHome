package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.LineaFactura;
import com.grupo3.pawHome.repositories.LineaFacturaRepository;
import org.springframework.stereotype.Service;

@Service
public class LineaFacturaService {
    private final LineaFacturaRepository lineaFacturaRepository;

    public LineaFacturaService(LineaFacturaRepository lineaFacturaRepository) {
        this.lineaFacturaRepository = lineaFacturaRepository;
    }

    public LineaFactura save(LineaFactura lineaFactura) { return lineaFacturaRepository.save(lineaFactura); }
}
