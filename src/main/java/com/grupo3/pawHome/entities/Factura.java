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
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private long precio;

    @Lob
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fecha;

    @OneToOne(mappedBy = "factura", cascade = CascadeType.ALL)
    private Pagos pagos;



}
