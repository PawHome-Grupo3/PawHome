package com.grupo3.pawHome.loaders;

import com.grupo3.pawHome.entities.Animales;
import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.AnimalesRepository;
import com.grupo3.pawHome.repositories.ApadrinarRepository;
import com.grupo3.pawHome.repositories.PerfilDatosRespository;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final AnimalesRepository animalesRepository;
    private final ApadrinarRepository apadrinarRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerfilDatosRespository perfilDatosRespository;

    public DataLoader(AnimalesRepository animalesRepository, ApadrinarRepository apadrinarRepository, UsuarioRepository usuarioRepository, PerfilDatosRespository perfilDatosRespository) {
        this.animalesRepository = animalesRepository;
        this.apadrinarRepository = apadrinarRepository;
        this.usuarioRepository = usuarioRepository;
        this.perfilDatosRespository = perfilDatosRespository;
    }

    @Override
    public void run(String... args) {

        // Crear Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("juan123");
        usuario.setPassword("passwordSegura");
        usuario.setEmail("juan@example.com");
        usuario.setFechaRegistro(LocalDate.now());

        // Crear PerfilDatos
        PerfilDatos perfil = new PerfilDatos();
        perfil.setNombre("Juan");
        perfil.setApellidos("Pérez Gómez");
        perfil.setEdad(30);
        perfil.setDni("12345678A");
        perfil.setDireccion("Calle Mayor 123");
        perfil.setCiudad("Madrid");
        perfil.setCp("28001");
        perfil.setTelefono1("600123456");
        perfil.setTelefono2("601234567");
        perfil.setTelefono3(null); // opcional
        perfil.setPuntosAcumulados(0);

        // Establecer relación bidireccional
        perfil.setUsuario(usuario);
        usuario.setPerfilDatos(perfil);

        // Guardar usuario (gracias a CascadeType.ALL también guarda perfil)
        usuarioRepository.save(usuario);

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
