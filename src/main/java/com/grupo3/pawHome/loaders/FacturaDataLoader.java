package com.grupo3.pawHome.loaders;

import com.grupo3.pawHome.entities.Factura;
import com.grupo3.pawHome.repositories.FacturaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FacturaDataLoader implements CommandLineRunner {

    private final FacturaRepository facturaRepository;
    public FacturaDataLoader(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Factura factura1 = new Factura();
        factura1.setPrecio(100);
        factura1.setDescripcion("Factura de prueba 1");
        factura1.setFecha(LocalDate.now());
        facturaRepository.save(factura1);

        Factura factura2 = new Factura();
        factura2.setPrecio(200);
        factura2.setDescripcion("Factura de prueba 2");
        factura2.setFecha(LocalDate.now());
        facturaRepository.save(factura2);

    }
}
