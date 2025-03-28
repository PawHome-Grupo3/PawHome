package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.PostgreSQLContainerTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.atm.buenas_practicas_java.entities.EntidadHija;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;


/**
 * Clase de prueba para la lógica de negocio correspondiente a la entidad "EntidadHija".
 *
 * <p>Esta clase contiene casos de prueba que verifican el correcto funcionamiento
 * de los métodos y servicios asociados a la entidad "EntidadHija". Utiliza un
 * entorno controlado para garantizar la exactitud de los resultados esperados.</p>
 *
 * <p>Se hacen uso de utilidades y dependencias, tales como frameworks para
 * pruebas unitarias, anotaciones para inyección de dependencias, entre otros,
 * para facilitar la implementación y ejecución de los tests.</p>
 *
 * <p>Los métodos de prueba de esta clase validan el comportamiento en diferentes
 * escenarios, incluyendo casos de éxito, fallos y excepciones esperadas.</p>
 *
 * @see EntidadHijaService
 */
@Log4j2
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntidadHijaServiceTest extends PostgreSQLContainerTest {


    @Autowired
    private EntidadHijaService entidadHijaService;

    @Test
    void testFindAllEntidadHija() {
        // Act
        List<EntidadHija> result = entidadHijaService.findAll();
        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void testFindByIdEntidadHija() {
        // Arrange
        EntidadHija entidadHija = new EntidadHija();
        entidadHija.setNombre("Entidad Test");
        EntidadHija savedEntidadHija = entidadHijaService.save(entidadHija);
        // Act
        Optional<EntidadHija> result = entidadHijaService.findById(savedEntidadHija.getId());
        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Entidad Test", result.get().getNombre());
    }

    @Test
    void testDeleteByIdEntidadHija() {
        // Arrange
        EntidadHija entidadHija = new EntidadHija();
        entidadHija.setNombre("Entidad Test");
        EntidadHija savedEntidadHija = entidadHijaService.save(entidadHija);
        // Act
        entidadHijaService.deleteById(savedEntidadHija.getId());
        Optional<EntidadHija> result = entidadHijaService.findById(savedEntidadHija.getId());
        // Assert
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void testUpdateEntidadHija() {
        // Arrange
        EntidadHija entidadHija = new EntidadHija();
        entidadHija.setNombre("Entidad Test");
        EntidadHija savedEntidadHija = entidadHijaService.save(entidadHija);
        savedEntidadHija.setNombre("Entidad Updated");
        // Act
        EntidadHija updatedEntidadHija = entidadHijaService.save(savedEntidadHija);
        // Assert
        Assertions.assertNotNull(updatedEntidadHija);
        Assertions.assertEquals("Entidad Updated", updatedEntidadHija.getNombre());
    }
    
    
}
