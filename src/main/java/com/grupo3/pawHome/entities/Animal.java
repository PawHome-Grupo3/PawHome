package com.grupo3.pawHome.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true)
    private String chip;

    private float peso;

    private String edad;

    @Column(name = "caracter_social")
    private boolean caracterSocial;

    @Column(length = 1000)
    private String descripcion;

    private boolean genero;

    private boolean origen;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean adoptado;

    @Column(name = "fecha_llegada")
    private LocalDate fechaLlegada;

    private boolean paseable;

    @Column(name = "animal_servicio")
    private boolean animalServicio;

    @Column(name = "ruta_img1")
    private String rutaImg1;

    @Column(name = "ruta_img2")
    private String rutaImg2;

    @Column(name = "ruta_img3")
    private String rutaImg3;

}
