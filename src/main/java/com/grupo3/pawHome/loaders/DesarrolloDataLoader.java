package com.grupo3.pawHome.loaders;

import com.grupo3.pawHome.entities.*;
import com.grupo3.pawHome.repositories.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@Log4j2
@Profile("desarrollo")
public class DesarrolloDataLoader {

    private final AnimalRepository animalRepository;
    private final ApadrinarRepository apadrinarRepository;
    private final UsuarioRepository usuarioRepository;
    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;
    private final TallaRepository tallaRepository;
    private final CategoriaRepository categoriaRepository;
    private final TarifaRepository tarifaRepository;
    private final PasswordEncoder passwordEncoder;

public DesarrolloDataLoader(AnimalRepository animalRepository,
                            ApadrinarRepository apadrinarRepository,
                            UsuarioRepository usuarioRepository,
                            FacturaRepository facturaRepository,
                            ProductoRepository productoRepository,
                            TallaRepository tallaRepository,
                            CategoriaRepository categoriaRepository,
                            TarifaRepository tarifaRepository,
                            PasswordEncoder passwordEncoder) {

    this.animalRepository = animalRepository;
    this.apadrinarRepository = apadrinarRepository;
    this.usuarioRepository = usuarioRepository;
    this.facturaRepository = facturaRepository;
    this.productoRepository = productoRepository;
    this.tallaRepository = tallaRepository;
    this.categoriaRepository = categoriaRepository;
    this.tarifaRepository = tarifaRepository;
    this.passwordEncoder = passwordEncoder;
}

    @PostConstruct
    public void loadDataDesarrollo() {
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

        // --- SERVICIOS --- //
        // Productos y tarifas de Guarderia
        Categoria c4 = new Categoria();
        c4.setNombre("Guarderia");
        categoriaRepository.save(c4);

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

        // Productos y tarifas de Peluqueria
        Categoria cPeluqueria = new Categoria();
        cPeluqueria.setNombre("Peluqueria");
        categoriaRepository.save(cPeluqueria);

        Producto pBañoBasico = new Producto();
        pBañoBasico.setNombre("BAÑO BÁSICO");
        pBañoBasico.setCategoria(cPeluqueria);
        productoRepository.save(pBañoBasico);

        Tarifa tBañoBasico = new Tarifa();
        tBañoBasico.setProducto(pBañoBasico);
        tBañoBasico.setCantidad(1);
        tBañoBasico.setPrecioUnitario(10.00);
        tBañoBasico.setFechaDesde(LocalDate.now());
        tBañoBasico.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tBañoBasico);

        Producto pBañoStripping = new Producto();
        pBañoStripping.setNombre("BAÑO Y STRIPPING");
        pBañoStripping.setCategoria(cPeluqueria);
        productoRepository.save(pBañoStripping);

        Tarifa tBañoStripping = new Tarifa();
        tBañoStripping.setProducto(pBañoStripping);
        tBañoStripping.setCantidad(1);
        tBañoStripping.setPrecioUnitario(15.00);
        tBañoStripping.setFechaDesde(LocalDate.now());
        tBañoStripping.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tBañoStripping);

        Producto pSoloCorte = new Producto();
        pSoloCorte.setNombre("SOLO CORTE");
        pSoloCorte.setCategoria(cPeluqueria);
        productoRepository.save(pSoloCorte);

        Tarifa tSoloCorte = new Tarifa();
        tSoloCorte.setProducto(pSoloCorte);
        tSoloCorte.setCantidad(1);
        tSoloCorte.setPrecioUnitario(15.00);
        tSoloCorte.setFechaDesde(LocalDate.now());
        tSoloCorte.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tSoloCorte);

        Producto pBañoCorte = new Producto();
        pBañoCorte.setNombre("BAÑO + CORTE");
        pBañoCorte.setCategoria(cPeluqueria);
        productoRepository.save(pBañoCorte);

        Tarifa tBañoCorte = new Tarifa();
        tBañoCorte.setProducto(pBañoCorte);
        tBañoCorte.setCantidad(1);
        tBañoCorte.setPrecioUnitario(20.00);
        tBañoCorte.setFechaDesde(LocalDate.now());
        tBañoCorte.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tBañoCorte);

        Producto pCompleto = new Producto();
        pCompleto.setNombre("COMPLETO");
        pCompleto.setCategoria(cPeluqueria);
        productoRepository.save(pCompleto);

        Tarifa tCompleto = new Tarifa();
        tCompleto.setProducto(pCompleto);
        tCompleto.setCantidad(1);
        tCompleto.setPrecioUnitario(25.00);
        tCompleto.setFechaDesde(LocalDate.now());
        tCompleto.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tCompleto);

        Producto pBañoAntiparasito = new Producto();
        pBañoAntiparasito.setNombre("Baño antiparásito");
        pBañoAntiparasito.setCategoria(cPeluqueria);
        productoRepository.save(pBañoAntiparasito);

        Tarifa tBañoAntiparasito = new Tarifa();
        tBañoAntiparasito.setProducto(pBañoAntiparasito);
        tBañoAntiparasito.setCantidad(1);
        tBañoAntiparasito.setPrecioUnitario(10.00);
        tBañoAntiparasito.setFechaDesde(LocalDate.now());
        tBañoAntiparasito.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tBañoAntiparasito);

        Producto pChampuColor = new Producto();
        pChampuColor.setNombre("Champú potenciador de color");
        pChampuColor.setCategoria(cPeluqueria);
        productoRepository.save(pChampuColor);

        Tarifa tChampuColor = new Tarifa();
        tChampuColor.setProducto(pChampuColor);
        tChampuColor.setCantidad(1);
        tChampuColor.setPrecioUnitario(10.00);
        tChampuColor.setFechaDesde(LocalDate.now());
        tChampuColor.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tChampuColor);

        Producto pBañoMedico = new Producto();
        pBañoMedico.setNombre("Baño medicado o spa para pieles sensibles");
        pBañoMedico.setCategoria(cPeluqueria);
        productoRepository.save(pBañoMedico);

        Tarifa tBañoMedico = new Tarifa();
        tBañoMedico.setProducto(pBañoMedico);
        tBañoMedico.setCantidad(1);
        tBañoMedico.setPrecioUnitario(15.00);
        tBañoMedico.setFechaDesde(LocalDate.now());
        tBañoMedico.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tBañoMedico);

        Producto pNudos = new Producto();
        pNudos.setNombre("Por nudos");
        pNudos.setCategoria(cPeluqueria);
        productoRepository.save(pNudos);

        Tarifa tNudos = new Tarifa();
        tNudos.setProducto(pNudos);
        tNudos.setCantidad(1);
        tNudos.setPrecioUnitario(7.00);
        tNudos.setFechaDesde(LocalDate.now());
        tNudos.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tNudos);

        Producto pDeslanado = new Producto();
        pDeslanado.setNombre("Deslanado");
        pDeslanado.setCategoria(cPeluqueria);
        productoRepository.save(pDeslanado);

        Tarifa tDeslanado = new Tarifa();
        tDeslanado.setProducto(pDeslanado);
        tDeslanado.setCantidad(1);
        tDeslanado.setPrecioUnitario(7.00);
        tDeslanado.setFechaDesde(LocalDate.now());
        tDeslanado.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tDeslanado);

        Producto pRetoqueExtra = new Producto();
        pRetoqueExtra.setNombre("Retoque extra en patas y cara");
        pRetoqueExtra.setCategoria(cPeluqueria);
        productoRepository.save(pRetoqueExtra);

        Tarifa tRetoqueExtra = new Tarifa();
        tRetoqueExtra.setProducto(pRetoqueExtra);
        tRetoqueExtra.setCantidad(1);
        tRetoqueExtra.setPrecioUnitario(5.00);
        tRetoqueExtra.setFechaDesde(LocalDate.now());
        tRetoqueExtra.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tRetoqueExtra);

        Producto pAntiolor = new Producto();
        pAntiolor.setNombre("Tratamiento antiolor con aceites esenciales");
        pAntiolor.setCategoria(cPeluqueria);
        productoRepository.save(pAntiolor);

        Tarifa tAntiolor = new Tarifa();
        tAntiolor.setProducto(pAntiolor);
        tAntiolor.setCantidad(1);
        tAntiolor.setPrecioUnitario(10.00);
        tAntiolor.setFechaDesde(LocalDate.now());
        tAntiolor.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tAntiolor);

        Producto pHidratacion = new Producto();
        pHidratacion.setNombre("Hidratación de almohadillas");
        pHidratacion.setCategoria(cPeluqueria);
        productoRepository.save(pHidratacion);

        Tarifa tHidratacion = new Tarifa();
        tHidratacion.setProducto(pHidratacion);
        tHidratacion.setCantidad(1);
        tHidratacion.setPrecioUnitario(7.00);
        tHidratacion.setFechaDesde(LocalDate.now());
        tHidratacion.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tHidratacion);

        Producto pPerfumeEspecial = new Producto();
        pPerfumeEspecial.setNombre("Aplicación de perfume especial");
        pPerfumeEspecial.setCategoria(cPeluqueria);
        productoRepository.save(pPerfumeEspecial);

        Tarifa tPerfumeEspecial = new Tarifa();
        tPerfumeEspecial.setProducto(pPerfumeEspecial);
        tPerfumeEspecial.setCantidad(1);
        tPerfumeEspecial.setPrecioUnitario(5.00);
        tPerfumeEspecial.setFechaDesde(LocalDate.now());
        tPerfumeEspecial.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tPerfumeEspecial);

        Producto pJuguete = new Producto();
        pJuguete.setNombre("Juguete < 5kg");
        pJuguete.setCategoria(cPeluqueria);
        productoRepository.save(pJuguete);

        Tarifa tJuguete = new Tarifa();
        tJuguete.setProducto(pJuguete);
        tJuguete.setCantidad(1);
        tJuguete.setPrecioUnitario(0.00);
        tJuguete.setFechaDesde(LocalDate.now());
        tJuguete.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tJuguete);

        Producto pPequeño = new Producto();
        pPequeño.setNombre("Pequeño 6 - 10kg");
        pPequeño.setCategoria(cPeluqueria);
        productoRepository.save(pPequeño);

        Tarifa tPequeño = new Tarifa();
        tPequeño.setProducto(pPequeño);
        tPequeño.setCantidad(1);
        tPequeño.setPrecioUnitario(6.00);
        tPequeño.setFechaDesde(LocalDate.now());
        tPequeño.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tPequeño);

        Producto pMediano = new Producto();
        pMediano.setNombre("Mediano 11 - 25kg");
        pMediano.setCategoria(cPeluqueria);
        productoRepository.save(pMediano);

        Tarifa tMediano = new Tarifa();
        tMediano.setProducto(pMediano);
        tMediano.setCantidad(1);
        tMediano.setPrecioUnitario(9.00);
        tMediano.setFechaDesde(LocalDate.now());
        tMediano.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tMediano);

        Producto pGrande = new Producto();
        pGrande.setNombre("Grande 26 - 35kg");
        pGrande.setCategoria(cPeluqueria);
        productoRepository.save(pGrande);

        Tarifa tGrande = new Tarifa();
        tGrande.setProducto(pGrande);
        tGrande.setCantidad(1);
        tGrande.setPrecioUnitario(12.00);
        tGrande.setFechaDesde(LocalDate.now());
        tGrande.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tGrande);

        Producto pGigante = new Producto();
        pGigante.setNombre("Gigante > 35kg");
        pGigante.setCategoria(cPeluqueria);
        productoRepository.save(pGigante);

        Tarifa tGigante = new Tarifa();
        tGigante.setProducto(pGigante);
        tGigante.setCantidad(1);
        tGigante.setPrecioUnitario(15.00);
        tGigante.setFechaDesde(LocalDate.now());
        tGigante.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tGigante);

        // Fin productos peluqueria
        // Productos Adiestramiento
        Categoria cAdiestramiento = new Categoria();
        cAdiestramiento.setNombre("Adiestramiento");
        categoriaRepository.save(cAdiestramiento);

        Producto pCursoCachorros = new Producto();
        pCursoCachorros.setNombre("Educación y preadiestramiento para cachorros");
        pCursoCachorros.setCategoria(cAdiestramiento);
        productoRepository.save(pCursoCachorros);

        Tarifa tCursoCachorros = new Tarifa();
        tCursoCachorros.setProducto(pCursoCachorros);
        tCursoCachorros.setCantidad(1);
        tCursoCachorros.setPrecioUnitario(200.00);
        tCursoCachorros.setFechaDesde(LocalDate.now());
        tCursoCachorros.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tCursoCachorros);

        Producto pCursoAdultos = new Producto();
        pCursoAdultos.setNombre("Educación y adiestramiento canino (adultos)");
        pCursoAdultos.setCategoria(cAdiestramiento);
        productoRepository.save(pCursoAdultos);

        Tarifa tCursoAdultos = new Tarifa();
        tCursoAdultos.setProducto(pCursoAdultos);
        tCursoAdultos.setCantidad(1);
        tCursoAdultos.setPrecioUnitario(200.00);
        tCursoAdultos.setFechaDesde(LocalDate.now());
        tCursoAdultos.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tCursoAdultos);

        Producto pAdiestramientoDomicilio = new Producto();
        pAdiestramientoDomicilio.setNombre("Educación y adiestramiento a domicilio");
        pAdiestramientoDomicilio.setCategoria(cAdiestramiento);
        productoRepository.save(pAdiestramientoDomicilio);

        Tarifa tAdiestramientoDomicilio = new Tarifa();
        tAdiestramientoDomicilio.setProducto(pAdiestramientoDomicilio);
        tAdiestramientoDomicilio.setCantidad(1);
        tAdiestramientoDomicilio.setPrecioUnitario(30.00);
        tAdiestramientoDomicilio.setFechaDesde(LocalDate.now());
        tAdiestramientoDomicilio.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tAdiestramientoDomicilio);

        Producto pAdiestramientoCentro = new Producto();
        pAdiestramientoCentro.setNombre("Educación y adiestramiento en el centro");
        pAdiestramientoCentro.setCategoria(cAdiestramiento);
        productoRepository.save(pAdiestramientoCentro);

        Tarifa tAdiestramientoCentro = new Tarifa();
        tAdiestramientoCentro.setProducto(pAdiestramientoCentro);
        tAdiestramientoCentro.setCantidad(1);
        tAdiestramientoCentro.setPrecioUnitario(20.00);
        tAdiestramientoCentro.setFechaDesde(LocalDate.now());
        tAdiestramientoCentro.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tAdiestramientoCentro);

        // Fin productos Adiestramiento
        // Productos Veterinario
        Categoria cVeterinario = new Categoria();
        cVeterinario.setNombre("Veterinario");
        categoriaRepository.save(cVeterinario);
        // --- PRODUCTOS Y TARIFAS ---

        // 1. Consulta Veterinaria
        Producto pConsultaVeterinaria = new Producto();
        pConsultaVeterinaria.setNombre("Consulta Veterinaria: el primer paso hacia la solución");
        pConsultaVeterinaria.setCategoria(cVeterinario);
        productoRepository.save(pConsultaVeterinaria);

        Tarifa tConsultaVeterinaria = new Tarifa();
        tConsultaVeterinaria.setProducto(pConsultaVeterinaria);
        tConsultaVeterinaria.setCantidad(1);
        tConsultaVeterinaria.setPrecioUnitario(25.00);
        tConsultaVeterinaria.setFechaDesde(LocalDate.now());
        tConsultaVeterinaria.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tConsultaVeterinaria);

        // 2. Chequeo Preventivo
        Producto pChequeoPreventivo = new Producto();
        pChequeoPreventivo.setNombre("Chequeo Preventivo: mejor prevenir que curar");
        pChequeoPreventivo.setCategoria(cVeterinario);
        productoRepository.save(pChequeoPreventivo);

        Tarifa tChequeoPreventivo = new Tarifa();
        tChequeoPreventivo.setProducto(pChequeoPreventivo);
        tChequeoPreventivo.setCantidad(1);
        tChequeoPreventivo.setPrecioUnitario(35.00);
        tChequeoPreventivo.setFechaDesde(LocalDate.now());
        tChequeoPreventivo.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tChequeoPreventivo);

        // 3. Vacunación
        Producto pVacunacion = new Producto();
        pVacunacion.setNombre("Vacunación: su escudo contra enfermedades");
        pVacunacion.setCategoria(cVeterinario);
        productoRepository.save(pVacunacion);

        Tarifa tVacunacion = new Tarifa();
        tVacunacion.setProducto(pVacunacion);
        tVacunacion.setCantidad(1);
        tVacunacion.setPrecioUnitario(30.00);
        tVacunacion.setFechaDesde(LocalDate.now());
        tVacunacion.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tVacunacion);

        // 4. Desparasitación
        Producto pDesparasitacion = new Producto();
        pDesparasitacion.setNombre("Desparasitación: libre de bichitos molestos");
        pDesparasitacion.setCategoria(cVeterinario);
        productoRepository.save(pDesparasitacion);

        Tarifa tDesparasitacion = new Tarifa();
        tDesparasitacion.setProducto(pDesparasitacion);
        tDesparasitacion.setCantidad(1);
        tDesparasitacion.setPrecioUnitario(20.00);
        tDesparasitacion.setFechaDesde(LocalDate.now());
        tDesparasitacion.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tDesparasitacion);

        // 5. Analítica y Test
        Producto pAnalitica = new Producto();
        pAnalitica.setNombre("Analítica y Test: saber es poder (y salud)");
        pAnalitica.setCategoria(cVeterinario);
        productoRepository.save(pAnalitica);

        Tarifa tAnalitica = new Tarifa();
        tAnalitica.setProducto(pAnalitica);
        tAnalitica.setCantidad(1);
        tAnalitica.setPrecioUnitario(45.00);
        tAnalitica.setFechaDesde(LocalDate.now());
        tAnalitica.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tAnalitica);

        // 6. Traumatología
        Producto pTraumatologia = new Producto();
        pTraumatologia.setNombre("Traumatología: cuidados para huesos y articulaciones");
        pTraumatologia.setCategoria(cVeterinario);
        productoRepository.save(pTraumatologia);

        Tarifa tTraumatologia = new Tarifa();
        tTraumatologia.setProducto(pTraumatologia);
        tTraumatologia.setCantidad(1);
        tTraumatologia.setPrecioUnitario(50.00);
        tTraumatologia.setFechaDesde(LocalDate.now());
        tTraumatologia.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tTraumatologia);

        // 7. Oftalmología
        Producto pOftalmologia = new Producto();
        pOftalmologia.setNombre("Oftalmología: ver bien es vivir mejor");
        pOftalmologia.setCategoria(cVeterinario);
        productoRepository.save(pOftalmologia);

        Tarifa tOftalmologia = new Tarifa();
        tOftalmologia.setProducto(pOftalmologia);
        tOftalmologia.setCantidad(1);
        tOftalmologia.setPrecioUnitario(50.00);
        tOftalmologia.setFechaDesde(LocalDate.now());
        tOftalmologia.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tOftalmologia);

        // 8. Radiografía
        Producto pRadiografia = new Producto();
        pRadiografia.setNombre("Radiografía: imágenes que cuentan la verdad");
        pRadiografia.setCategoria(cVeterinario);
        productoRepository.save(pRadiografia);

        Tarifa tRadiografia = new Tarifa();
        tRadiografia.setProducto(pRadiografia);
        tRadiografia.setCantidad(1);
        tRadiografia.setPrecioUnitario(40.00);
        tRadiografia.setFechaDesde(LocalDate.now());
        tRadiografia.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tRadiografia);

        // 9. Ecografía
        Producto pEcografia = new Producto();
        pEcografia.setNombre("Ecografía: exploración con alta precisión");
        pEcografia.setCategoria(cVeterinario);
        productoRepository.save(pEcografia);

        Tarifa tEcografia = new Tarifa();
        tEcografia.setProducto(pEcografia);
        tEcografia.setCantidad(1);
        tEcografia.setPrecioUnitario(45.00);
        tEcografia.setFechaDesde(LocalDate.now());
        tEcografia.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tEcografia);

        // 10. Cirugía veterinaria
        Producto pCirugia = new Producto();
        pCirugia.setNombre("Cirugía veterinaria: en manos expertas");
        pCirugia.setCategoria(cVeterinario);
        productoRepository.save(pCirugia);

        Tarifa tCirugia = new Tarifa();
        tCirugia.setProducto(pCirugia);
        tCirugia.setCantidad(1);
        tCirugia.setPrecioUnitario(100.00);
        tCirugia.setFechaDesde(LocalDate.now());
        tCirugia.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tCirugia);

        // 11. Limpieza bucodental
        Producto pLimpiezaBucodental = new Producto();
        pLimpiezaBucodental.setNombre("Limpieza bucodental: sonrisa sana, vida larga");
        pLimpiezaBucodental.setCategoria(cVeterinario);
        productoRepository.save(pLimpiezaBucodental);

        Tarifa tLimpiezaBucodental = new Tarifa();
        tLimpiezaBucodental.setProducto(pLimpiezaBucodental);
        tLimpiezaBucodental.setCantidad(1);
        tLimpiezaBucodental.setPrecioUnitario(60.00);
        tLimpiezaBucodental.setFechaDesde(LocalDate.now());
        tLimpiezaBucodental.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tLimpiezaBucodental);

        // 12. Hospitalización
        Producto pHospitalizacion = new Producto();
        pHospitalizacion.setNombre("Hospitalización: atención las 24 horas");
        pHospitalizacion.setCategoria(cVeterinario);
        productoRepository.save(pHospitalizacion);

        Tarifa tHospitalizacion = new Tarifa();
        tHospitalizacion.setProducto(pHospitalizacion);
        tHospitalizacion.setCantidad(1);
        tHospitalizacion.setPrecioUnitario(45.00);
        tHospitalizacion.setFechaDesde(LocalDate.now());
        tHospitalizacion.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tHospitalizacion);

        // 13. Visita a domicilio
        Producto pVisitaDomicilio = new Producto();
        pVisitaDomicilio.setNombre("Visita a domicilio: el veterinario va a ti");
        pVisitaDomicilio.setCategoria(cVeterinario);
        productoRepository.save(pVisitaDomicilio);

        Tarifa tVisitaDomicilio = new Tarifa();
        tVisitaDomicilio.setProducto(pVisitaDomicilio);
        tVisitaDomicilio.setCantidad(1);
        tVisitaDomicilio.setPrecioUnitario(30.00);
        tVisitaDomicilio.setFechaDesde(LocalDate.now());
        tVisitaDomicilio.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tVisitaDomicilio);

        // 14. Control de Leishmania
        Producto pLeishmania = new Producto();
        pLeishmania.setNombre("Control de Leishmania: prevención todo el año");
        pLeishmania.setCategoria(cVeterinario);
        productoRepository.save(pLeishmania);

        Tarifa tLeishmania = new Tarifa();
        tLeishmania.setProducto(pLeishmania);
        tLeishmania.setCantidad(1);
        tLeishmania.setPrecioUnitario(35.00);
        tLeishmania.setFechaDesde(LocalDate.now());
        tLeishmania.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tLeishmania);

        // 15. Control de obesidad
        Producto pObesidad = new Producto();
        pObesidad.setNombre("Control de obesidad: cuerpo sano, vida feliz");
        pObesidad.setCategoria(cVeterinario);
        productoRepository.save(pObesidad);

        Tarifa tObesidad = new Tarifa();
        tObesidad.setProducto(pObesidad);
        tObesidad.setCantidad(1);
        tObesidad.setPrecioUnitario(30.00);
        tObesidad.setFechaDesde(LocalDate.now());
        tObesidad.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tObesidad);

        // 16. Plan de salud para perros
        Producto pPlanPerros = new Producto();
        pPlanPerros.setNombre("Plan de salud para perros: cuídalo todo el año");
        pPlanPerros.setCategoria(cVeterinario);
        productoRepository.save(pPlanPerros);

        Tarifa tPlanPerros = new Tarifa();
        tPlanPerros.setProducto(pPlanPerros);
        tPlanPerros.setCantidad(1);
        tPlanPerros.setPrecioUnitario(150.00);
        tPlanPerros.setFechaDesde(LocalDate.now());
        tPlanPerros.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tPlanPerros);

        // 17. Plan de salud para gatos
        Producto pPlanGatos = new Producto();
        pPlanGatos.setNombre("Plan de salud para gatos: bienestar sin estrés");
        pPlanGatos.setCategoria(cVeterinario);
        productoRepository.save(pPlanGatos);

        Tarifa tPlanGatos = new Tarifa();
        tPlanGatos.setProducto(pPlanGatos);
        tPlanGatos.setCantidad(1);
        tPlanGatos.setPrecioUnitario(140.00);
        tPlanGatos.setFechaDesde(LocalDate.now());
        tPlanGatos.setFechaHasta(LocalDate.of(2026, 1, 1));
        tarifaRepository.save(tPlanGatos);

        // Fin productos Veterinario

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

        Animal a1 = new Animal();
        a1.setNombre("Firulais");
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

