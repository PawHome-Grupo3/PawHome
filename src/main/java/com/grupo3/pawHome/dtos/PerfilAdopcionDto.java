package com.grupo3.pawHome.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilAdopcionDto {
    private int adopcionId;
    private String rutaImg;
    private String fechaDocumento;
    private String nombre;
    private Boolean aceptado;
}
