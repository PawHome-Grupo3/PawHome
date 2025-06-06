package com.grupo3.pawHome.dtos;

import com.grupo3.pawHome.entities.Producto;
import com.grupo3.pawHome.entities.Talla;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarritoDTO {
    private Producto producto;
    private Talla talla;
    private int cantidad;
    private double precioUnitario;
}
