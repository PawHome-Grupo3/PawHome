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
public class Factura {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double precio;

    @Column(length = 5000)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fecha;
}
