package com.grupo3.pawHome.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDTO {
    private Integer idFactura;
    private String fecha;
    private double precio;
    private String descripcion;
    private String nombreUsuario;
    private String apellidosUsuario;
    private String direccionUsuario;
    private String ciudadUsuario;
    private String cpUsuario;
    private String dniUsuario;
    private String telefonoUsuario;
    private List<LineaFacturaDTO> lineas;
}

