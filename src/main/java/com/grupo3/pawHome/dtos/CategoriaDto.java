package com.grupo3.pawHome.dtos;

import com.grupo3.pawHome.entities.Categoria;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class CategoriaDto implements Serializable {
    private final String nombre;
    private final int id;

    public CategoriaDto(Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = procesarNombre(categoria.getNombre());
    }

    private String procesarNombre(String nombre) {
        return nombre.replaceAll("\\s+", "").toLowerCase();
    }

}