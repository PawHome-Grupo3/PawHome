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
public class Pagos {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String autorizacion;

    @Column(nullable = false)
    private boolean estado;

    @Column(nullable = false)
    private String pagoscol;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    private Factura idFactura;


}
