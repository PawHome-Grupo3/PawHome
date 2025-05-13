package com.grupo3.pawHome.loaders;

import com.grupo3.pawHome.entities.Pagos;
import com.grupo3.pawHome.repositories.FacturaRepository;
import com.grupo3.pawHome.repositories.PagosRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Log4j2
@Profile("local")
public class PagosLocalDataLoader {
    private final PagosRepository pagosRepository;
    private final FacturaRepository facturaRepository;

    public PagosLocalDataLoader(FacturaRepository facturaRepository, PagosRepository pagosRepository) {
        this.pagosRepository = pagosRepository;
        this.facturaRepository = facturaRepository;
    }

    @PostConstruct
    public void loadData() {
        log.info("Cargando datos de prueba para Pagos");
        int numeroEntidades = 10;
        Pagos[] pagos = new Pagos[numeroEntidades];
        Arrays.setAll(pagos, i -> {
            Pagos pago = new Pagos();
            pago.setPrecio(100 + i);
            pago.setDescripcion("Pago de prueba " + (i + 1));
            pago.setFecha(LocalDate.now());
            return pago;
        });

        log.info("Datos de prueba para Pagos cargados correctamente");

    }
}



