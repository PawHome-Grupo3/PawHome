package com.grupo3.pawHome.loaders;

import com.grupo3.pawHome.entities.Animales;
import com.grupo3.pawHome.repositories.AnimalesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final AnimalesRepository animalesRepository;

    public DataLoader(AnimalesRepository animalesRepository) {
        this.animalesRepository = animalesRepository;
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
                "img/firulais.jpg"
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
                "img/michi.jpg"
        ));
    }
}
