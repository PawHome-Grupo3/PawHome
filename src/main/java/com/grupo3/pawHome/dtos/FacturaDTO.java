package com.grupo3.pawHome.dtos;

import com.grupo3.pawHome.entities.Factura;
import com.grupo3.pawHome.entities.LineaFactura;
import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String dniUsuario;
    private String telefonoUsuario;
    private List<LineaFacturaDTO> lineas;
}

