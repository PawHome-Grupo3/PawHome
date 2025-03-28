package com.atm.buenas_practicas_java;

//https://stackoverflow.com/a/77113817/3617531


import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class PostgreSQLContainerTest {

    @ServiceConnection
    protected static final PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>("postgres:latest").withDatabaseName("testDB").withUsername("appuser").withPassword("password123");

    static {
        dbContainer.start();
    }

}

