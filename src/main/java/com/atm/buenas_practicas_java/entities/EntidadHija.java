package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * Representa una clase de entidad persistente denominada "EntidadHija".
 *
 * Esta clase está asociada a una tabla en la base de datos mediante JPA y mapea
 * los datos relacionados con una entidad hija que depende de una entidad padre.
 *
 * Anotaciones de la clase:
 * - {@code @Entity}: Define esta clase como una entidad JPA que será persistida en la base de datos.
 * - {@code @Getter} y {@code @Setter}: Proporcionadas por Lombok, generan automáticamente
 *   los métodos getter y setter correspondientes para todos los atributos.
 * - {@code @AllArgsConstructor}: Genera un constructor con argumentos para todos los campos de la clase.
 * - {@code @NoArgsConstructor}: Genera un constructor sin argumentos, útil para JPA y frameworks de serialización.
 *
 * Atributos:
 *
 * - {@code id}:
 *   - Descripción: Clave primaria de la tabla que representa a esta entidad.
 *   - Anotaciones:
 *     - {@code @Id}: Define esta propiedad como la clave primaria.
 *     - {@code @GeneratedValue(strategy = GenerationType.IDENTITY)}: El valor del identificador será generado
 *       automáticamente en la base de datos mediante la estrategia de incremento automático (IDENTITY).
 *
 * - {@code nombre}:
 *   - Descripción: Almacena el nombre asociado a esta entidad hija.
 *
 * - {@code entidadPadre}:
 *   - Descripción: Relación de muchos-a-uno con la entidad {@code EntidadPadre}.
 *   - Anotaciones:
 *     - {@code @ManyToOne(fetch = FetchType.EAGER)}: Define la relación de muchos-a-uno con la tabla asociada
 *       de {@code EntidadPadre}. La estrategia de carga {@code EAGER} asegura que los datos de la entidad
 *       padre sean cargados inmediatamente al acceder a la entidad hija.
 *
 * Constructores:
 *
 * - {@code EntidadHija()}: Constructor vacío sin argumentos. Requerido por JPA.
 * - {@code EntidadHija(int id, String nombre, EntidadPadre entidadPadre)}: Constructor que inicializa todos los
 *   campos de la clase.
 * - {@code EntidadHija(String nombre)}: Constructor especial que inicializa solo el campo {@code nombre}.
 *
 * Relaciones:
 *
 * - Asociación: Muchas instancias de {@code EntidadHija} pueden estar asociadas a una sola instancia
 *   de {@code EntidadPadre}.
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EntidadHija  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    private EntidadPadre entidadPadre;

    /**
     * Constructor de la clase {@code EntidadHija}.
     *
     * Inicializa una nueva instancia de {@code EntidadHija} con el nombre especificado.
     *
     * @param nombre El nombre que se le asignará a la instancia de {@code EntidadHija}.
     */
    public EntidadHija (String nombre) {
        this.nombre = nombre;
    }
}