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
public class PerfilDatos {

    @Id
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private int edad;

    @Column(nullable = false)
    private String dni;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String pais;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String cp;

    @Column(nullable = false)
    private String telefono1;

    private String telefono2;

    private String telefono3;

    @OneToOne
    @MapsId  // Indica que usuarioId viene de usuario.id
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
