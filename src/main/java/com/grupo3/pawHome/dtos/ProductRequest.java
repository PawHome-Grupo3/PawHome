package com.grupo3.pawHome.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long amount;
    private Long quantity;
    private String name;
    private String currency;

    public ProductRequest(String nombreProducto, int cantidadDias) {
        this.name = nombreProducto;
        this.quantity = (long)cantidadDias;
    }
}
