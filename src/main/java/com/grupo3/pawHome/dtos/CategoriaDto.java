package com.grupo3.pawHome.dtos;

import com.grupo3.pawHome.entities.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CategoriaDto implements Serializable {
    private final String nombre;
    private final int id;

    // Constructor normal (procesamiento general: sin espacios, lowercase)
    public CategoriaDto(Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = procesarNombreBasico(categoria.getNombre());
    }

    // Constructor especializado para nombres tipo "tienda-Collar"
    public CategoriaDto(Categoria categoria, boolean eliminarPrefijoTienda) {
        this.id = categoria.getId();
        if (eliminarPrefijoTienda) {
            this.nombre = procesarNombreTienda(categoria.getNombre());
        } else {
            this.nombre = procesarNombreBasico(categoria.getNombre());
        }
    }

    private String procesarNombreBasico(String nombre) {
        return nombre == null ? "" : nombre.replaceAll("\\s+", "").toLowerCase();
    }

    private String procesarNombreTienda(String nombre) {
        if (nombre == null) return "";
        return nombre.replaceFirst("(?i)^tienda-?", "").trim();
    }
}