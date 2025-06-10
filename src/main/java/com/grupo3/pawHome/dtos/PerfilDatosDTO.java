package com.grupo3.pawHome.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDatosDTO {
    private String nombre;
    private String apellidos;
    private int edad;
    private String dni;
    private String direccion;
    private String pais;
    private String ciudad;
    private String cp;
    private String telefono1;
    private String telefono2;
    private String telefono3;
}