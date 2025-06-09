package com.grupo3.pawHome.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tipo_pago")
public class TipoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @OneToMany(mappedBy = "tipoPago", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MetodoPago> metodoPago;
}
