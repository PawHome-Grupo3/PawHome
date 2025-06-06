package com.grupo3.pawHome.util;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("paisUtils")
public class PaisUtils {
    public String obtenerNombrePais(String sigla) {
        if (sigla == null || sigla.isBlank()) {
            return "País desconocido";
        }
        String codigoPais = sigla.toUpperCase();
        String nombrePais = Locale.of("es", codigoPais).getDisplayCountry();
        return nombrePais.substring(0, 1).toUpperCase() + nombrePais.substring(1).toLowerCase();
    }
}
