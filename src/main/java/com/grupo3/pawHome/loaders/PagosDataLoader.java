package com.grupo3.pawHome.loaders;

import com.grupo3.pawHome.entities.Factura;
import com.grupo3.pawHome.entities.Pagos;
import com.grupo3.pawHome.repositories.FacturaRepository;
import com.grupo3.pawHome.repositories.PagosRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PagosDataLoader implements CommandLineRunner {


    private final PagosRepository pagosRepository;
    private final FacturaRepository facturaRepository;
    public PagosDataLoader(PagosRepository pagosRepository, FacturaRepository facturaRepository) {
        this.pagosRepository = pagosRepository;
        this.facturaRepository = facturaRepository;
    }

     @Override
     public void run(String... args) throws Exception {
         Factura factura = facturaRepository.findById(1L).orElseThrow();
         Pagos pagos1 = new Pagos();
         pagos1.setAutorizacion("123456");
         pagos1.setEstado(true);
         pagos1.setFactura(factura); // Assuming you have a factura with ID 1
         pagosRepository.save(pagos1);

         factura = facturaRepository.findById(2L).orElseThrow();
         Pagos pagos2 = new Pagos();
         pagos2.setAutorizacion("654321");
         pagos2.setEstado(false);
         pagos2.setFactura(factura); // Assuming you have a factura with ID 2
         pagosRepository.save(pagos2);
     }
}



