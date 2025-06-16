package com.grupo3.pawHome.services;

import com.grupo3.pawHome.dtos.FacturaDTO;
import com.grupo3.pawHome.dtos.LineaFacturaDTO;
import com.grupo3.pawHome.entities.Factura;
import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.repositories.FacturaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public Factura save(Factura factura) { return facturaRepository.save(factura); }

    public List<FacturaDTO> obtenerFacturasPorUsuario(Integer usuarioId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Factura> facturas = facturaRepository.findByUsuarioId(usuarioId);

        return facturas.stream().map(factura -> {
            PerfilDatos perfil = factura.getUsuario().getPerfilDatos();

            List<LineaFacturaDTO> lineasDTO = factura.getLineaFacturas().stream()
                    .map(linea -> {
                        double precioUnitario = (linea.getTarifa() != null)
                                ? linea.getTarifa().getPrecioUnitario()
                                : linea.getFactura().getPrecio();

                        return new LineaFacturaDTO(
                                linea.getNombre(),
                                linea.getDescripcion(),
                                linea.getCantidad(),
                                precioUnitario
                        );
                    })
                    .collect(Collectors.toList());

            return new FacturaDTO(
                    factura.getId(),
                    factura.getFecha().format(formatter),
                    factura.getPrecio(), // Ojo: también debes convertir el total
                    factura.getDescripcion(),
                    perfil.getNombre(),
                    perfil.getApellidos(),
                    perfil.getDireccion(),
                    perfil.getDni(),
                    perfil.getTelefono1(),
                    lineasDTO
            );
        }).collect(Collectors.toList());
    }
}
