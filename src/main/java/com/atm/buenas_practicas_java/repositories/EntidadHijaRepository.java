package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.EntidadHija;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio de acceso a datos para la entidad {@code EntidadHija}.
 *
 * Proporciona funcionalidades CRUD predefinidas gracias a la extensión de {@code JpaRepository}.
 * Este repositorio es una capa de abstracción que interactúa con la base de datos a través de JPA.
 *
 * Anotaciones utilizadas:
 *
 * {@code @Repository}:
 * - Aunque no está explícita en el código, Spring reconoce las interfaces que extienden
 *   {@code JpaRepository} como componentes del repositorio, y las trata automáticamente como beans.
 * - Marca este componente como parte de la capa de acceso a datos.
 * - Maneja excepciones específicas de JPA, convirtiéndolas en excepciones unchecked de Spring.
 *
 * Funciones principales heredadas de {@code JpaRepository}:
 * - {@code save(EntidadHija entity)}: Guarda o actualiza una instancia de {@code EntidadHija}.
 * - {@code findById(Integer id)}: Recupera una instancia de {@code EntidadHija} por su ID.
 * - {@code findAll()}: Obtiene todas las instancias de {@code EntidadHija} almacenadas.
 * - {@code deleteById(Integer id)}: Elimina una instancia de {@code EntidadHija} directamente por su ID.
 */
public interface EntidadHijaRepository extends JpaRepository<EntidadHija, Long> {
    Optional<Object> findByNombre(String hijaActualizada);
}