package com.grupo3.pawHome.loaders;

import com.grupo3.pawHome.entities.*;
import com.grupo3.pawHome.repositories.*;
import com.grupo3.pawHome.entities.Animal;
import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.AnimalRepository;
import com.grupo3.pawHome.repositories.ApadrinarRepository;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final AnimalRepository animalRepository;
    private final ApadrinarRepository apadrinarRepository;
    private final UsuarioRepository usuarioRepository;
    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;
    private final TallaRepository tallaRepository;
    private final CategoriaRepository categoriaRepository;
    private final TarifaRepository tarifaRepository;
    private final PasswordEncoder passwordEncoder;
    private final RazaRepository razaRepository;
    private final EspecieRepository especieRepository;


    public DataLoader(AnimalRepository animalRepository,
                      ApadrinarRepository apadrinarRepository,
                      UsuarioRepository usuarioRepository,
                      FacturaRepository facturaRepository,
                      ProductoRepository productoRepository,
                      TallaRepository tallaRepository,
                      CategoriaRepository categoriaRepository,
                      TarifaRepository tarifaRepository,
                      PasswordEncoder passwordEncoder, RazaRepository razaRepository, EspecieRepository especieRepository) {

        this.animalRepository = animalRepository;
        this.apadrinarRepository = apadrinarRepository;
        this.usuarioRepository = usuarioRepository;
        this.facturaRepository = facturaRepository;
        this.productoRepository = productoRepository;
        this.tallaRepository = tallaRepository;
        this.categoriaRepository = categoriaRepository;
        this.tarifaRepository = tarifaRepository;
        this.passwordEncoder = passwordEncoder;
        this.razaRepository = razaRepository;
        this.especieRepository = especieRepository;
    }

    @Override
    public void run(String... args) {
        // Crear Usuarios
        Usuario u1 = new Usuario();
        u1.setNickname("Juan1234");
        u1.setPassword(passwordEncoder.encode("123"));
        u1.setEmail("a@gmail.com");
        u1.setFechaRegistro(LocalDate.now());
        usuarioRepository.save(u1);

        Usuario u2 = new Usuario();
        u2.setNickname("Maria456");
        u2.setPassword(passwordEncoder.encode("123"));
        u2.setEmail("maria@gmail.com");
        u2.setFechaRegistro(LocalDate.now());
        usuarioRepository.save(u2);

        // Crear Facturas para u1
        Factura f1 = new Factura();
        f1.setDescripcion("Compra alimento");
        f1.setFecha(LocalDate.now());
        f1.setPrecio(150.00);
        f1.setUsuario(u1);  // Asocia factura a usuario
        facturaRepository.save(f1);

        Factura f2 = new Factura();
        f2.setDescripcion("Consulta veterinaria");
        f2.setFecha(LocalDate.now());
        f2.setPrecio(200.00);
        f2.setUsuario(u1);
        facturaRepository.save(f2);

        // Crear Facturas para u2
        Factura f3 = new Factura();
        f3.setDescripcion("Accesorios mascota");
        f3.setFecha(LocalDate.now());
        f3.setPrecio(75.00);
        f3.setUsuario(u2);
        facturaRepository.save(f3);

        Categoria c1 = new Categoria();
        c1.setNombre("Collares");
        categoriaRepository.save(c1);

        Categoria c2 = new Categoria();
        c2.setNombre("Camisetas");
        categoriaRepository.save(c2);

        Categoria c3 = new Categoria();
        c3.setNombre("Tazas");
        categoriaRepository.save(c3);

        Categoria c4 = new Categoria();
        c4.setNombre("Guarderia");
        categoriaRepository.save(c4);

        Producto p1 = new Producto();
        p1.setNombre("Collar");
        p1.setDescripcion("Dale a tu peludo amigo el mejor look con nuestro collar para perro, diseñado para brindar seguridad, confort y un toque de estilo único.");
        p1.setRutaImagen1("/images/collar.png");
        p1.setRutaImagen2("/images/Camiseta-azul.png");
        p1.setCategoria(c1);
        productoRepository.save(p1);

        Producto p2 = new Producto();
        p2.setNombre("Camiseta Azul");
        p2.setDescripcion("¿Quieres ser un héroe con estilo? Compra nuestra camiseta para que lo sepa todo el mundo");
        p2.setRutaImagen1("/images/Camiseta-azul.png");
        p2.setCategoria(c2);
        productoRepository.save(p2);

        Producto p3 = new Producto();
        p3.setNombre("Camiseta Héroe");
        p3.setDescripcion("¿Quieres ser un héroe con estilo? Compra nuestra camiseta para que lo sepa todo el mundo");
        p3.setRutaImagen1("/images/Camiseta-azul.png");
        p3.setCategoria(c2);
        productoRepository.save(p3);

        Producto p4 = new Producto();
        p4.setNombre("Camiseta Héroe v2");
        p4.setDescripcion("¿Quieres ser un héroe con estilo? Compra nuestra camiseta para que lo sepa todo el mundo");
        p4.setRutaImagen1("/images/Camiseta-azul.png");
        p4.setCategoria(c2);
        productoRepository.save(p4);

        Producto p5 = new Producto();
        p5.setNombre("Taza todo es mejor");
        p5.setDescripcion("Dale a tu peludo amigo el mejor look con nuestro collar para perro, diseñado para brindar seguridad, confort y un toque de estilo único.");
        p5.setRutaImagen1("/images/taza-prueba.jpg");
        p5.setCategoria(c3);
        productoRepository.save(p5);

        Producto p6 = new Producto();
        p6.setNombre("ESTANCIA EXPRESS");
        p6.setCategoria(c4);
        productoRepository.save(p6);

        Tarifa tExpress = new Tarifa();
        tExpress.setProducto(p6);
        tExpress.setCantidad(1);
        tExpress.setPrecioUnitario(18.00);
        tExpress.setFechaDesde(LocalDate.now());
        tExpress.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tExpress);

        Producto p7 = new Producto();
        p7.setNombre("ESTANCIA RELAX");
        p7.setCategoria(c4);
        productoRepository.save(p7);

        Tarifa tRelax = new Tarifa();
        tRelax.setProducto(p7);
        tRelax.setCantidad(1);
        tRelax.setPrecioUnitario(15.00);
        tRelax.setFechaDesde(LocalDate.now());
        tRelax.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tRelax);

        Producto p8 = new Producto();
        p8.setNombre("ESTANCIA SABÁTICA");
        p8.setCategoria(c4);
        productoRepository.save(p8);

        Tarifa tSabatica = new Tarifa();
        tSabatica.setProducto(p8);
        tSabatica.setCantidad(1);
        tSabatica.setPrecioUnitario(12.00);
        tSabatica.setFechaDesde(LocalDate.now());
        tSabatica.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tSabatica);

        Producto p9 = new Producto();
        p9.setNombre("BONO LARGA ESTANCIA");
        p9.setCategoria(c4);
        productoRepository.save(p9);

        Tarifa bonoLargaEstancia = new Tarifa();
        bonoLargaEstancia.setProducto(p9);
        bonoLargaEstancia.setCantidad(1);
        bonoLargaEstancia.setPrecioUnitario(200.00);
        bonoLargaEstancia.setFechaDesde(LocalDate.now());
        bonoLargaEstancia.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(bonoLargaEstancia);

        Producto p10 = new Producto();
        p10.setNombre("BONO ESTANCIA SOLO DÍA");
        p10.setCategoria(c4);
        productoRepository.save(p10);

        Tarifa bonoSoloDia = new Tarifa();
        bonoSoloDia.setProducto(p10);
        bonoSoloDia.setCantidad(1);
        bonoSoloDia.setPrecioUnitario(75.00);
        bonoSoloDia.setFechaDesde(LocalDate.now());
        bonoSoloDia.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(bonoSoloDia);

        Talla t1 = new Talla();
        t1.setStock(5);
        t1.setTallaje("S");
        t1.setProducto(p1);
        tallaRepository.save(t1);

        Talla t2 = new Talla();
        t2.setStock(2);
        t2.setTallaje("M");
        t2.setProducto(p1);
        tallaRepository.save(t2);

        Talla t3 = new Talla();
        t3.setStock(37);
        t3.setTallaje("L");
        t3.setProducto(p1);
        tallaRepository.save(t3);

        Talla t4 = new Talla();
        t4.setStock(10);
        t4.setTallaje("M");
        t4.setProducto(p2);
        tallaRepository.save(t4);

        Talla t5 = new Talla();
        t5.setStock(0);
        t5.setTallaje("L");
        t5.setProducto(p2);
        tallaRepository.save(t5);

        Talla t6 = new Talla();
        t6.setStock(10);
        t6.setTallaje("unica");
        t6.setProducto(p3);
        tallaRepository.save(t6);

        // Crear Usuario
        Usuario usuario = new Usuario();
        usuario.setNickname("juan123");
        usuario.setPassword(passwordEncoder.encode("1234"));
        usuario.setEmail("juan@example.com");
        usuario.setFechaRegistro(LocalDate.now());

        // Crear PerfilDatos
        PerfilDatos perfil = new PerfilDatos();
        perfil.setNombre("Juan");
        perfil.setApellidos("Pérez Gómez");
        perfil.setEdad(30);
        perfil.setDni("12345678A");
        perfil.setDireccion("Calle Mayor 123");
        perfil.setPais("España");
        perfil.setCiudad("Madrid");
        perfil.setCp("28001");
        perfil.setTelefono1("600123456");
        perfil.setTelefono2("601234567");
        perfil.setTelefono3(null); // opcional

        // Establecer relación bidireccional
        perfil.setUsuario(usuario);
        usuario.setPerfilDatos(perfil);

        // Guardar usuario (gracias a CascadeType.ALL también guarda perfil)
        usuarioRepository.save(usuario);

        Talla t7 = new Talla();
        t7.setStock(12);
        t7.setTallaje("unica");
        t7.setProducto(p4);
        tallaRepository.save(t7);

        Talla t8 = new Talla();
        t8.setStock(15);
        t8.setTallaje("unica");
        t8.setProducto(p5);
        tallaRepository.save(t8);

        Tarifa ta1 = new Tarifa();
        ta1.setProducto(p1);
        ta1.setCantidad(1);
        ta1.setPrecioUnitario(18.20);
        ta1.setFechaDesde(LocalDate.now());
        ta1.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(ta1);

        Tarifa ta2 = new Tarifa();
        ta2.setProducto(p1);
        ta2.setCantidad(1);
        ta2.setPrecioUnitario(30.20);
        ta2.setFechaDesde(LocalDate.of(2024, 1, 1));
        ta2.setFechaHasta(LocalDate.of(2025, 1, 1));
        tarifaRepository.save(ta2);

        Tarifa ta6 = new Tarifa();
        ta6.setProducto(p2);
        ta6.setCantidad(1);
        ta6.setPrecioUnitario(18.20);
        ta6.setFechaDesde(LocalDate.now());
        ta6.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(ta6);

        Tarifa ta3 = new Tarifa();
        ta3.setProducto(p3);
        ta3.setCantidad(1);
        ta3.setPrecioUnitario(18.20);
        ta3.setFechaDesde(LocalDate.now());
        ta3.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(ta3);

        Tarifa ta4 = new Tarifa();
        ta4.setProducto(p4);
        ta4.setCantidad(1);
        ta4.setPrecioUnitario(18.20);
        ta4.setFechaDesde(LocalDate.now());
        ta4.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(ta4);

        Tarifa ta5 = new Tarifa();
        ta5.setProducto(p5);
        ta5.setCantidad(1);
        ta5.setPrecioUnitario(18.20);
        ta5.setFechaDesde(LocalDate.now());
        ta5.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(ta5);

        Raza raza = new Raza();
        raza.setNombre("Perro");
        razaRepository.save(raza);

        Raza raza2 = new Raza();
        raza2.setNombre("Gato");
        razaRepository.save(raza2);

        Especie e1 = new Especie();
        e1.setNombre("Malinois");
        e1.setRaza(raza);
        especieRepository.save(e1);

        Especie e3 = new Especie();
        e3.setNombre("Dálmata");
        e3.setRaza(raza);
        especieRepository.save(e3);

        Especie e2 = new Especie();
        e2.setNombre("Esfinge");
        e2.setRaza(raza2);
        especieRepository.save(e2);

        Animal a1 = new Animal();
        a1.setNombre("Comodoro");
        a1.setChip("CHIP9999");
        a1.setPeso(20f);
        a1.setFechaNacimiento(LocalDate.of(2020, 6, 11));
        a1.setCaracterSocial(true);
        a1.setDescripcion("Perro muy amigable");
        a1.setGenero(true);
        a1.setOrigen(false);
        a1.setAdoptado(false);
        a1.setFechaLlegada(LocalDate.of(2024, 4, 10));
        a1.setPaseable(true);
        a1.setAnimalServicio(false);
        a1.setRutaImg1("/images/perro1Card.jpg");
        a1.setRutaImg2("/images/default-example.png");
        a1.setRutaImg3("/images/default-example.png");
        a1.setEspecie(e1);
        animalRepository.save(a1);



        Animal a2 = new Animal();
        a2.setNombre("Michi");
        a2.setChip("CHIP98765");
        a2.setPeso(4f);
        a2.setFechaNacimiento(LocalDate.of(2024, 6, 11));
        a2.setCaracterSocial(false);
        a2.setDescripcion("Michi es un gato tímido pero dulce, que ha pasado por una situación difícil y está aprendiendo a confiar nuevamente en las personas. Le encanta observar desde la ventana y es muy curioso con los juguetes. Necesita un hogar tranquilo y paciente.");
        a2.setGenero(false);
        a2.setOrigen(true);
        a2.setAdoptado(false);
        a2.setFechaLlegada(LocalDate.of(2024, 5, 1));
        a2.setPaseable(false);
        a2.setAnimalServicio(false);
        a2.setRutaImg1("/images/perro1.png");
        a2.setRutaImg2("/images/default-example.png");
        a2.setRutaImg3("/images/default-example.png");
        a2.setEspecie(e2);
        animalRepository.save(a2);

        Animal a3 = new Animal();
        a3.setNombre("Firulais");
        a3.setChip("CHIP10102");
        a3.setPeso(18f);
        a3.setFechaNacimiento(LocalDate.of(2025, 6, 1));
        a3.setCaracterSocial(true);
        a3.setDescripcion("Firulais es un perro muy enérgico, sociable y siempre dispuesto a jugar. Ideal para una familia con espacio o niños, disfruta de los paseos largos y se lleva bien con otros animales. Está vacunado y desparasitado, listo para ir a su nuevo hogar.");
        a3.setGenero(true);
        a3.setOrigen(false);
        a3.setAdoptado(false);
        a3.setFechaLlegada(LocalDate.of(2023, 8, 15));
        a3.setPaseable(true);
        a3.setAnimalServicio(false);
        a3.setRutaImg1("/images/perro2.png");
        a3.setRutaImg2("/images/default-example.png");
        a3.setRutaImg3("/images/default-example.png");
        a3.setEspecie(e2);
        animalRepository.save(a3);

        Animal a4 = new Animal();
        a4.setNombre("Luna");
        a4.setChip("CHIP54321");
        a4.setPeso(6f);
        a4.setFechaNacimiento(LocalDate.of(2018, 7, 21));
        a4.setCaracterSocial(true);
        a4.setDescripcion("Luna es una gata muy curiosa y activa. Le encanta explorar su entorno y encontrar rincones nuevos donde dormir la siesta. Es sociable con otros gatos y muy cariñosa con las personas una vez que toma confianza. Ideal para un hogar con ventanas soleadas.");
        a4.setGenero(false);
        a4.setOrigen(true);
        a4.setAdoptado(true);
        a4.setFechaLlegada(LocalDate.of(2024, 2, 10));
        a4.setPaseable(true);
        a4.setAnimalServicio(false);
        a4.setRutaImg1("/images/perro3.png");
        a4.setRutaImg2("/images/default-example.png");
        a4.setRutaImg3("/images/default-example.png");
        a4.setEspecie(e3);
        animalRepository.save(a4);

        Animal a5 = new Animal();
        a5.setNombre("Thor");
        a5.setChip("CHIP11223");
        a5.setPeso(25f);
        a5.setFechaNacimiento(LocalDate.of(2025, 6, 2));
        a5.setCaracterSocial(true);
        a5.setDescripcion("Thor es un perro de servicio entrenado, tranquilo y obediente. Tiene un gran sentido de la calma y es ideal para acompañar a personas con necesidades especiales. Se adapta bien a distintos entornos y responde perfectamente a comandos básicos y avanzados.");
        a5.setGenero(true);
        a5.setOrigen(false);
        a5.setAdoptado(true);
        a5.setFechaLlegada(LocalDate.of(2022, 11, 5));
        a5.setPaseable(true);
        a5.setAnimalServicio(true);
        a5.setRutaImg1("/images/perro4.png");
        a5.setRutaImg2("/images/default-example.png");
        a5.setRutaImg3("/images/default-example.png");
        a5.setEspecie(e3);
        animalRepository.save(a5);

        Animal a6 = new Animal();
        a6.setNombre("Nube");
        a6.setChip("CHIP77889");
        a6.setPeso(3.5f);
        a6.setFechaNacimiento(LocalDate.of(2022, 2, 2));
        a6.setCaracterSocial(false);
        a6.setDescripcion("Nube es un gatito rescatado recientemente, extremadamente juguetón y curioso. A pesar de su corta edad, ya demuestra ser muy inteligente y está en proceso de aprender a usar el rascador y el arenero. Le encantan los juguetes con plumas y perseguir sombras.");
        a6.setGenero(false);
        a6.setOrigen(false);
        a6.setAdoptado(false);
        a6.setFechaLlegada(LocalDate.of(2024, 4, 25));
        a6.setPaseable(false);
        a6.setAnimalServicio(false);
        a6.setRutaImg1("images/perro5.png");
        a6.setRutaImg2("/images/default-example.png");
        a6.setRutaImg3("/images/default-example.png");
        a6.setEspecie(e3);
        animalRepository.save(a6);

        Animal a7 = new Animal();
        a7.setNombre("Roco");
        a7.setChip("CHIP99887");
        a7.setPeso(30f);
        a7.setFechaNacimiento(LocalDate.of(2021, 8, 5));
        a7.setCaracterSocial(true);
        a7.setDescripcion("Roco es un gran danés de carácter tranquilo, muy amigable y paciente. A pesar de su tamaño, es un gigante gentil que disfruta de la compañía humana. Ideal para personas con experiencia en razas grandes. Le gusta estar acompañado y no ladra con frecuencia.");
        a7.setGenero(true);
        a7.setOrigen(false);
        a7.setAdoptado(true);
        a7.setFechaLlegada(LocalDate.of(2023, 1, 12));
        a7.setPaseable(true);
        a7.setAnimalServicio(false);
        a7.setRutaImg1("/images/perro6.png");
        a7.setRutaImg2("/images/default-example.png");
        a7.setRutaImg3("/images/default-example.png");
        animalRepository.save(a7);

        Apadrinar ap1 = new Apadrinar();
        ap1.setAporteMensual(10.0);
        ap1.setFechaInicio(LocalDate.of(2024, 2, 22));
        ap1.setFechaRenovacion(LocalDate.of(2025, 6, 22));
        ap1.setFechaBaja(null);
        ap1.setAnimal(a1);
        ap1.setUsuario(u1);
        apadrinarRepository.save(ap1);

        Apadrinar ap2 = new Apadrinar();
        ap2.setAporteMensual(10.0);
        ap2.setFechaInicio(LocalDate.of(2024, 5, 1));
        ap2.setFechaRenovacion(LocalDate.of(2025, 6, 1));
        ap2.setFechaBaja(null);
        ap2.setAnimal(a2);
        ap2.setUsuario(u1);
        apadrinarRepository.save(ap2);

        Apadrinar ap3 = new Apadrinar();
        ap3.setAporteMensual(10.0);
        ap3.setFechaInicio(LocalDate.of(2025, 2, 1));
        ap3.setFechaRenovacion(null);
        ap3.setFechaBaja(LocalDate.of(2025, 3, 1));
        ap3.setAnimal(a3);
        ap3.setUsuario(u1);
        apadrinarRepository.save(ap3);

        Apadrinar ap4 = new Apadrinar();
        ap4.setAporteMensual(10.0);
        ap4.setFechaInicio(LocalDate.of(2025, 2, 1));
        ap4.setFechaRenovacion(LocalDate.of(2025, 6, 1));
        ap4.setFechaBaja(null);
        ap4.setAnimal(a4);
        ap4.setUsuario(u2);
        apadrinarRepository.save(ap4);
    }
}
