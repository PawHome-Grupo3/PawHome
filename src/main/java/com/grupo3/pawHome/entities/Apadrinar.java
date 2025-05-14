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
public class Apadrinar {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private double aporteMensual;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_renovacion")
    private LocalDate fechaRenovacion;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id")
    private Animales animal;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;
}
