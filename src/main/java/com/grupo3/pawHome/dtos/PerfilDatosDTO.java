package com.grupo3.pawHome.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilDatosDTO {
    private String nombre;
    private String apellidos;
    private int edad;
    private String dni;
    private String direccion;
    private String ciudad;
    private String cp;
    private String telefono1;
    private String telefono2;
    private String telefono3;
}