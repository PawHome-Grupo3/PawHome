package com.grupo3.pawHome.loaders;

import com.grupo3.pawHome.entities.*;
import com.grupo3.pawHome.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final AnimalesRepository animalesRepository;
    private final ApadrinarRepository apadrinarRepository;
    private final UsuarioRepository usuarioRepository;
    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;
    private final TallaRepository tallaRepository;
    private final CategoriaRepository categoriaRepository;
    private final TarifaRepository tarifaRepository;


    public DataLoader(AnimalesRepository animalesRepository, ApadrinarRepository apadrinarRepository, UsuarioRepository usuarioRepository, FacturaRepository facturaRepository, ProductoRepository productoRepository, TallaRepository tallaRepository, CategoriaRepository categoriaRepository, TarifaRepository tarifaRepository) {
        this.animalesRepository = animalesRepository;
        this.apadrinarRepository = apadrinarRepository;
        this.usuarioRepository = usuarioRepository;
        this.facturaRepository = facturaRepository;
        this.productoRepository = productoRepository;
        this.tallaRepository = tallaRepository;
        this.categoriaRepository = categoriaRepository;
        this.tarifaRepository = tarifaRepository;
    }

    @Override
    public void run(String... args) {
        // Crear Usuarios
        Usuario u1 = new Usuario();
        u1.setNombre("Juan123");
        u1.setPassword("1234");
        u1.setEmail("a@gmail.com");
        u1.setFechaRegistro(LocalDate.now());
        usuarioRepository.save(u1);

        Usuario u2 = new Usuario();
        u2.setNombre("Maria456");
        u2.setPassword("abcd");
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

        Producto p1 = new Producto();
        p1.setNombre("Collar");
        p1.setDescripcion("Dale a tu peludo amigo el mejor look con nuestro collar para perro, diseñado para brindar seguridad, confort y un toque de estilo único.");
        p1.setRutaImagen1("/images/collar.png");
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

        Tarifa ta1 = new Tarifa();
        ta1.setProducto(p1);
        ta1.setCantidad(4);
        ta1.setPrecioUnitario(18.20);
        ta1.setFechaDesde(LocalDate.now());
        ta1.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(ta1);

        Animales a1 = new Animales();
        a1.setNombre("Firulais");
        a1.setChip("CHIP9999");
        a1.setPeso(20f);
        a1.setEdad("2 años");
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
        animalesRepository.save(a1);

        Animales a2 = new Animales();
        a2.setNombre("Michi");
        a2.setChip("CHIP98765");
        a2.setPeso(4f);
        a2.setEdad("1 año");
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
        animalesRepository.save(a2);

        Animales a3 = new Animales();
        a3.setNombre("Firulais");
        a3.setChip("CHIP10102");
        a3.setPeso(18f);
        a3.setEdad("3 años");
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
        animalesRepository.save(a3);

        Animales a4 = new Animales();
        a4.setNombre("Luna");
        a4.setChip("CHIP54321");
        a4.setPeso(6f);
        a4.setEdad("2 años");
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
        animalesRepository.save(a4);

        Animales a5 = new Animales();
        a5.setNombre("Thor");
        a5.setChip("CHIP11223");
        a5.setPeso(25f);
        a5.setEdad("4 años");
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
        animalesRepository.save(a5);

        Animales a6 = new Animales();
        a6.setNombre("Nube");
        a6.setChip("CHIP77889");
        a6.setPeso(3.5f);
        a6.setEdad("6 meses");
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
        animalesRepository.save(a6);

        Animales a7 = new Animales();
        a7.setNombre("Roco");
        a7.setChip("CHIP99887");
        a7.setPeso(30f);
        a7.setEdad("5 años");
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
        animalesRepository.save(a7);

        Apadrinar ap1 = new Apadrinar();
        ap1.setAporteMensual(5.0);
        ap1.setFechaInicio(LocalDate.of(2024, 5, 1));
        ap1.setFechaRenovacion(LocalDate.of(2024, 5, 1));
        ap1.setFechaBaja(null);
        ap1.setAnimal(a1);
        ap1.setUsuario(u1);
        apadrinarRepository.save(ap1);

        Apadrinar ap2 = new Apadrinar();
        ap2.setAporteMensual(5.0);
        ap2.setFechaInicio(LocalDate.of(2024, 5, 1));
        ap2.setFechaRenovacion(LocalDate.of(2023, 6, 1));
        ap2.setFechaBaja(null);
        ap2.setAnimal(a2);
        ap2.setUsuario(u1);
        apadrinarRepository.save(ap2);

        Apadrinar ap3 = new Apadrinar();
        ap3.setAporteMensual(10.0);
        ap3.setFechaInicio(LocalDate.of(2025, 2, 1));
        ap3.setFechaRenovacion(LocalDate.of(2025, 3, 1));
        ap3.setFechaBaja(LocalDate.of(2025, 3, 1));
        ap3.setAnimal(a3);
        ap3.setUsuario(u1);
        apadrinarRepository.save(ap3);

        Apadrinar ap4 = new Apadrinar();
        ap4.setAporteMensual(10.0);
        ap4.setFechaInicio(LocalDate.of(2025, 2, 1));
        ap4.setFechaRenovacion(LocalDate.of(2029, 3, 1));
        ap4.setFechaBaja(LocalDate.of(2030, 3, 1));
        ap4.setAnimal(a4);
        ap4.setUsuario(u1);
        apadrinarRepository.save(ap4);
    }
}
