package com.grupo3.pawHome.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Raza {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @OneToMany(mappedBy = "raza", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Animal> animales;

    @ManyToOne
    @JoinColumn(name = "especie_id", nullable = false)
    private Especie especie;
}
