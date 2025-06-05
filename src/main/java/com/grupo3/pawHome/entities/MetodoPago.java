package com.grupo3.pawHome.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "metodos_pago")
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;        // 'monetario' o 'material'
    private String nombre;      // PayPal, Bizum, Correos
    private String url;
    private String descripcion;

}
