package com.grupo3.pawHome.loaders;

import com.grupo3.pawHome.entities.Animales;
import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.AnimalesRepository;
import com.grupo3.pawHome.repositories.ApadrinarRepository;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final AnimalesRepository animalesRepository;
    private final ApadrinarRepository apadrinarRepository;
    private final UsuarioRepository usuarioRepository;

    public DataLoader(AnimalesRepository animalesRepository, ApadrinarRepository apadrinarRepository, UsuarioRepository usuarioRepository) {
        this.animalesRepository = animalesRepository;
        this.apadrinarRepository = apadrinarRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        usuarioRepository.save(new Usuario(
                0L,

                "Juan Pérez",

                "1234",

                "juan.perez@example.com",

                LocalDate.now())
        );

        usuarioRepository.save(new Usuario(
                0L,

                "Paulo",

                "1234",

                "apulo@example.com",

                LocalDate.now())
        );

        animalesRepository.save(new Animales(
                0L,
                "Firulais",
                "CHIP12345",
                "20kg",
                "2 años",
                true,
                "Perro muy amigable",
                true,
                false,
                false,
                LocalDate.of(2024, 4, 10),
                true,
                false,
                "/images/perro1Card.jpg"
        ));

        animalesRepository.save(new Animales(
                0L,
                "Michi",
                "CHIP98765",
                "4kg",
                "1 año",
                false,
                "Gato tímido pero dulce",
                false,
                true,
                false,
                LocalDate.of(2024, 5, 1),
                false,
                false,
                "/images/gato.mira.arriba.jpg"
        ));

        apadrinarRepository.save(new Apadrinar(
                0L,
                5.0,
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2025, 6, 1),
                null,
                animalesRepository.findById(1L).get(),
                usuarioRepository.findById(1L).get()
        ));

        apadrinarRepository.save(new Apadrinar(
                0L,
                5.0,
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2023, 6, 1),
                null,
                animalesRepository.findById(1L).get(),
                usuarioRepository.findById(1L).get()
        ));

        apadrinarRepository.save(new Apadrinar(
                0L,
                10.0,
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 1),
                animalesRepository.findById(2L).get(),
                usuarioRepository.findById(1L).get()
        ));

        apadrinarRepository.save(new Apadrinar(
                0L,
                10.0,
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2029, 3, 1),
                LocalDate.of(2030, 3, 1),
                animalesRepository.findById(2L).get(),
                usuarioRepository.findById(1L).get()
        ));
    }
}
