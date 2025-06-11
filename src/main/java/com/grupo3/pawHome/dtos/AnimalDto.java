package com.grupo3.pawHome.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDto {
    private Integer id;
    private String nombre;
    private String chip;
    private float peso;
    private LocalDate fechaNacimiento;
    private boolean caracterSocial;
    private String descripcion;
    private boolean genero;
    private boolean origen;
    private boolean adoptado;
    private LocalDate fechaLlegada;
    private boolean paseable;
    private boolean animalServicio;
    private String rutaImg1;
    private String rutaImg2;
    private String rutaImg3;
    private String stripeProductId;
    private String edad;
}

