package com.grupo3.pawHome.entities;

import com.grupo3.pawHome.PostgreSQLContainerTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Log4j2
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntidadPadreTest extends PostgreSQLContainerTest {

    @Test
    void testEntidadPadreConstructorWithNombre() {
        // Arrange
        String expectedNombre = "Test Nombre";

        // Act
        EntidadPadre entidadPadre = new EntidadPadre(expectedNombre);

        // Assert
        assertEquals(expectedNombre, entidadPadre.getNombre(), "El nombre no fue inicializado correctamente en el constructor.");
    }


}
