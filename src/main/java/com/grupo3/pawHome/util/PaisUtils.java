package com.grupo3.pawHome.util;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("paisUtils")
public class PaisUtils {

    public String obtenerNombrePais(String sigla) {
        if (sigla == null || sigla.isBlank()) {
            return "País desconocido";
        }

        // Usamos Locale.of que está disponible desde Java 22
        Locale locale = Locale.of("es", sigla.toUpperCase());
        String nombrePais = locale.getDisplayCountry(Locale.of("es"));

        return nombrePais.isBlank() ? "País desconocido" : nombrePais;
    }

    public String obtenerCodigoDesdeNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) return null;

        for (String iso : Locale.getISOCountries()) {
            Locale locale = Locale.of("es", iso);
            if (locale.getDisplayCountry(Locale.of("es")).equalsIgnoreCase(nombre)) {
                return iso;
            }
        }
        return null;
    }
}
