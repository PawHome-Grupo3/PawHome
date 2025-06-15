package com.grupo3.pawHome.dtos;

import com.grupo3.pawHome.entities.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormAdoptaDTO {
    Integer usuarioId;
    String nombre;
    String dni;
    Integer edad;
    String telefono;
    String correo;
    String direccion;
    String ocupacion;
    String otrosAnimales;
    String deAcuerdo;
    String hijos;
    String caracteristicaMascota;
    String experienciaPrevia;
    String tiempoSolo;
    String dondeVivira;
    String veterinario;
    String visitasSeguimiento;
    String firma;
    LocalDate fechaFormulario;
    Animal animal;

    public FormAdoptaDTO (Integer usuarioId, String nombre, String dni, int edad,
                          String telefono, String correo, String direccion) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.dni = dni;
        this.edad = edad;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }
}


