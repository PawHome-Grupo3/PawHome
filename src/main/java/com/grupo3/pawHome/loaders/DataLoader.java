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
                "CHIP9999",
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
                "CHIP98789",
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

        animalesRepository.save(new Animales(
                0L,
                "Michi",
                "CHIP98765",
                "4kg",
                "1 año",
                false,
                "Michi es un gato tímido pero dulce, que ha pasado por una situación difícil y está aprendiendo a confiar nuevamente en las personas. Le encanta observar desde la ventana y es muy curioso con los juguetes. Necesita un hogar tranquilo y paciente.",
                false,
                true,
                false,
                LocalDate.of(2024, 5, 1),
                false,
                false,
                "/images/perro1.png"
        ));

        animalesRepository.save(new Animales(
                0L,
                "Firulais",
                "CHIP10102",
                "18kg",
                "3 años",
                true,
                "Firulais es un perro muy enérgico, sociable y siempre dispuesto a jugar. Ideal para una familia con espacio o niños, disfruta de los paseos largos y se lleva bien con otros animales. Está vacunado y desparasitado, listo para ir a su nuevo hogar.",
                true,
                false,
                false,
                LocalDate.of(2023, 8, 15),
                true,
                false,
                "/images/perro2.png"
        ));

        animalesRepository.save(new Animales(
                0L,
                "Luna",
                "CHIP54321",
                "6kg",
                "2 años",
                true,
                "Luna es una gata muy curiosa y activa. Le encanta explorar su entorno y encontrar rincones nuevos donde dormir la siesta. Es sociable con otros gatos y muy cariñosa con las personas una vez que toma confianza. Ideal para un hogar con ventanas soleadas.",
                false,
                true,
                true,
                LocalDate.of(2024, 2, 10),
                true,
                false,
                "/images/perro3.png"
        ));

        animalesRepository.save(new Animales(
                0L,
                "Thor",
                "CHIP11223",
                "25kg",
                "4 años",
                true,
                "Thor es un perro de servicio entrenado, tranquilo y obediente. Tiene un gran sentido de la calma y es ideal para acompañar a personas con necesidades especiales. Se adapta bien a distintos entornos y responde perfectamente a comandos básicos y avanzados.",
                true,
                false,
                true,
                LocalDate.of(2022, 11, 5),
                true,
                true,
                "/images/perro4.png"
        ));

        animalesRepository.save(new Animales(
                0L,
                "Nube",
                "CHIP77889",
                "3.5kg",
                "6 meses",
                false,
                "Nube es un gatito rescatado recientemente, extremadamente juguetón y curioso. A pesar de su corta edad, ya demuestra ser muy inteligente y está en proceso de aprender a usar el rascador y el arenero. Le encantan los juguetes con plumas y perseguir sombras.",
                false,
                false,
                false,
                LocalDate.of(2024, 4, 25),
                false,
                false,
                "/images/perro5.png"
        ));

        animalesRepository.save(new Animales(
                0L,
                "Roco",
                "CHIP99887",
                "30kg",
                "5 años",
                true,
                "Roco es un gran danés de carácter tranquilo, muy amigable y paciente. A pesar de su tamaño, es un gigante gentil que disfruta de la compañía humana. Ideal para personas con experiencia en razas grandes. Le gusta estar acompañado y no ladra con frecuencia.",
                true,
                false,
                true,
                LocalDate.of(2023, 1, 12),
                true,
                false,
                "/images/perro6.png"
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
