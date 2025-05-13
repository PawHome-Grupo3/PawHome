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

    private LocalDate fecha_inicio;

    private LocalDate fecha_baja;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animales animalesId;
}
