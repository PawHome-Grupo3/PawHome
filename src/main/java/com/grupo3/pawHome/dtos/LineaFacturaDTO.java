package com.grupo3.pawHome.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineaFacturaDTO {
    private String nombre;
    private String descripcion;
    private int cantidad;
    private double precioUnitario; // sacado desde la entidad `Tarifa`
}

