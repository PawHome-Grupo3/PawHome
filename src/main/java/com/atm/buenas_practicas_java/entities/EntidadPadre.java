package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Representa una clase de entidad persistente denominada "EntidadPadre".
 *
 * Anotaciones de clase:
 *
 * - {@code @Entity}: Marca esta clase como una entidad de JPA, lo que significa que será mapeada a una tabla
 *   en la base de datos. Es necesaria para que JPA/Hibernate reconozca la clase como una entidad persistente.
 *
 * - {@code @Getter}: Proporcionada por Lombok, genera automáticamente métodos "getter" para todos los atributos,
 *   lo que reduce el código repetitivo necesario para acceder a los miembros de clase.
 *
 * - {@code @Setter}: También parte de Lombok, genera automáticamente métodos "setter" para todos los atributos,
 *   facilitando la modificación de los valores de los campos.
 *
 * - {@code @AllArgsConstructor}: Generada por Lombok, crea un constructor que acepta un argumento por cada atributo
 *   de la clase. Útil para inicializar instancias fácilmente.
 *
 * - {@code @NoArgsConstructor}: También proporcionada por Lombok, genera un constructor sin argumentos,
 *   lo cual es útil especialmente al trabajar con herramientas de persistencia como JPA, que requieren
 *   dicho constructor para ciertas operaciones (ejemplo: reflección al cargar entidades).
 *
 * Campos:
 *
 * - {@code id}: Representa un identificador único para esta entidad. Este campo actúa como clave primaria
 *   en la tabla correspondiente de la base de datos.
 *
 *   - Anotaciones:
 *     - {@code @Id}: Indica a JPA que este campo es el identificador único de la entidad (Primary Key).
 *     - {@code @GeneratedValue(strategy = GenerationType.IDENTITY)}: Señala que el valor del campo se generará automáticamente
 *       en la base de datos, utilizando una estrategia de incremento automático (IDENTITY).
 *
 * - {@code nombre}: Un campo que almacena el nombre asociado con esta entidad.
 *
 * - {@code entidadesHijas}: Una lista que representa la relación asociativa uno-a-muchos (One-to-Many) con la clase
 *   `EntidadHija`. En este caso, una instancia de `EntidadPadre` puede tener asociadas múltiples instancias de `EntidadHija`.
 *
 *   - Anotaciones:
 *     - {@code @OneToMany(mappedBy = "entidadPadre", cascade = CascadeType.ALL)}: Especifica que esta relación es de uno-a-muchos,
 *       y que el mapeo recíproco en `EntidadHija` está definido por el atributo `entidadPadre`. Con el uso de
 *       {@code cascade = CascadeType.ALL}, todas las operaciones realizadas sobre la entidad padre (como insertar o eliminar)
 *       se propagarán automáticamente a las entidades hijas asociadas.
 *
 * Funcionalidad:
 *
 * La clase define un modelo para el almacenamiento y recuperación de datos en una tabla que representa relaciones
 * jerárquicas. La dependencia entre padre e hijos se gestiona a través de `entidadesHijas`.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EntidadPadre {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @OneToMany( mappedBy = "entidadPadre", fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntidadHija> entidadesHijas;

    /**
     * Constructor de la clase EntidadPadre que permite inicializar la entidad
     * con un atributo específico.
     *
     * @param nombre Nombre de la entidad padre. Este campo representa el nombre
     *               asociado con esta instancia de la entidad.
     */
    public EntidadPadre(String nombre) {
        this.nombre = nombre;
    }
}



