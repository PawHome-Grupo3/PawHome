package com.grupo3.pawHome.loaders;

import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsuarioDataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    // Inyectamos el repositorio a través del constructor
    public UsuarioDataLoader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Cargando datos iniciales de usuarios...");

        // Insertamos algunos usuarios de ejemplo en la base de datos
        usuarioRepository.save(new Usuario(
                0L,

                "Juan Pérez",

                "1234",

                "juan.perez@example.com",

                LocalDate.now())
        );


        System.out.println("Usuarios cargados correctamente.");
    }
}

