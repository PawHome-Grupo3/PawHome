package com.grupo3.pawHome.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="linea_factura")
public class LineaFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private int id;

    private String nombre;

    private int cantidad;

    @Column(length=300)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "tarifa_id", referencedColumnName = "id")
    private Tarifa tarifa;
}
