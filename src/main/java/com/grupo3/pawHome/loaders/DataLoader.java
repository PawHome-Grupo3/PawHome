package com.grupo3.pawHome.loaders;

import com.grupo3.pawHome.entities.Animales;
import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.repositories.AnimalesRepository;
import com.grupo3.pawHome.repositories.ApadrinarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final AnimalesRepository animalesRepository;
    private final ApadrinarRepository apadrinarRepository;

    public DataLoader(AnimalesRepository animalesRepository, ApadrinarRepository apadrinarRepository) {
        this.animalesRepository = animalesRepository;
        this.apadrinarRepository = apadrinarRepository;
    }

    @Override
    public void run(String... args) {
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
                LocalDate.of(2024, 6, 1),
                animalesRepository.findById(1L).get()
        ));

        apadrinarRepository.save(new Apadrinar(
                0L,
                10.0,
                LocalDate.of(2025, 2, 1),
                null,
                animalesRepository.findById(2L).get()
        ));
    }
}
