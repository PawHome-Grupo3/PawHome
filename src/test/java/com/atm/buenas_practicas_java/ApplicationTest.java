package com.atm.buenas_practicas_java;

import com.atm.buenas_practicas_java.repositories.EntidadHijaRepository;
import com.atm.buenas_practicas_java.repositories.EntidadPadreRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationTest extends PostgreSQLContainerTest {

    @Autowired
    EntidadPadreRepository entidadPadreService;

    @Autowired
    EntidadHijaRepository entidadHijaService;


    @Autowired
    EntidadPadreRepository entidadPadreRepository;

    @Autowired
    EntidadHijaRepository entidadHijaRepository;

    /**
     * Ensures that the Spring application context loads successfully when the main method is called.
     */
    @Test
    @Order(1)
    void contextLoads() {
        log.info("Iniciando la prueba de contextos...");
        assertThat(dbContainer.isRunning()).isTrue();
        assertThat(entidadHijaRepository.findAll()).isEmpty();
        assertThat(entidadPadreRepository.findAll()).isEmpty();
        for (String s : Arrays.asList("Context loaded", "Database name: " + dbContainer.getDatabaseName(), "Username: " + dbContainer.getUsername(), "Password: " + dbContainer.getPassword())) {
            log.info(s);
        }
        log.info("Contexto de Spring cargado con éxito.");
    }


}
