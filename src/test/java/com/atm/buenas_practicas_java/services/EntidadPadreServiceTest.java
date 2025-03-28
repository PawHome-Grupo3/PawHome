package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.PostgreSQLContainerTest;
import com.atm.buenas_practicas_java.entities.EntidadPadre;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


/**
 * Clase de prueba para la lógica de negocio correspondiente a la entidad "EntidadPadre".
 *
 * <p>Esta clase contiene casos de prueba que verifican el correcto funcionamiento
 * de los métodos y servicios asociados a la entidad "EntidadPadre". Utiliza un
 * entorno controlado para garantizar la exactitud de los resultados esperados.</p>
 *
 * <p>Se hacen uso de utilidades y dependencias, tales como frameworks para
 * pruebas unitarias, anotaciones para inyección de dependencias, entre otros,
 * para facilitar la implementación y ejecución de los tests.</p>
 *
 * <p>Los métodos de prueba de esta clase validan el comportamiento en diferentes
 * escenarios, incluyendo casos de éxito, fallos y excepciones esperadas.</p>
 *
 * @see EntidadPadreService
 */
@Log4j2
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntidadPadreServiceTest extends PostgreSQLContainerTest {


    @Autowired
    private EntidadPadreService entidadPadreService;

    @Test
    void testFindAllEntidadPadre() {
        EntidadPadre entidadPadre = new EntidadPadre();
        entidadPadre.setNombre("Entidad Test");
        entidadPadreService.save(entidadPadre);
        List<EntidadPadre> result = entidadPadreService.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void testFindByIdEntidadPadre() {
        // Arrange
        EntidadPadre entidadPadre = new EntidadPadre();
        entidadPadre.setNombre("Entidad Test");
        EntidadPadre savedEntidadPadre = entidadPadreService.save(entidadPadre);
        // Act
        Optional<EntidadPadre> result = entidadPadreService.findById(savedEntidadPadre.getId());
        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Entidad Test", result.get().getNombre());
    }

    @Test
    void testDeleteByIdEntidadPadre() {
        // Arrange
        EntidadPadre entidadPadre = new EntidadPadre();
        entidadPadre.setNombre("Entidad Test");
        EntidadPadre savedEntidadPadre = entidadPadreService.save(entidadPadre);
        // Act
        entidadPadreService.deleteById(savedEntidadPadre.getId());
        Optional<EntidadPadre> result = entidadPadreService.findById(savedEntidadPadre.getId());
        // Assert
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void testUpdateEntidadPadre() {
        // Arrange
        EntidadPadre entidadPadre = new EntidadPadre();
        entidadPadre.setNombre("Entidad Test");
        EntidadPadre savedEntidadPadre = entidadPadreService.save(entidadPadre);
        savedEntidadPadre.setNombre("Entidad Updated");
        // Act
        EntidadPadre updatedEntidadPadre = entidadPadreService.save(savedEntidadPadre);
        // Assert
        Assertions.assertNotNull(updatedEntidadPadre);
        Assertions.assertEquals("Entidad Updated", updatedEntidadPadre.getNombre());
    }


}
