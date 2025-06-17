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
public class Adopcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ocupacion;

    private String otrosAnimales;

    private String deAcuerdo;

    private String hijos;

    private String caracteristicaMascota;

    private String experienciaPrevia;

    private String tiempoSolo;

    private String dondeVivira;

    private String veterinario;

    private String visitasSeguimiento;

    private String firma;

    private LocalDate fechaFormulario;

    private Boolean aceptado;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id")
    private Animal animal;

}
