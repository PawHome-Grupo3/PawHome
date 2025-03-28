package com.atm.buenas_practicas_java.integrationTests;

import com.atm.buenas_practicas_java.PostgreSQLContainerTest;
import com.atm.buenas_practicas_java.entities.EntidadHija;
import com.atm.buenas_practicas_java.entities.EntidadPadre;
import com.atm.buenas_practicas_java.repositories.EntidadHijaRepository;
import com.atm.buenas_practicas_java.repositories.EntidadPadreRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@AutoConfigureCache
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationIT extends PostgreSQLContainerTest {

    @Autowired
    EntidadPadreRepository entidadPadreService;

    @Autowired
    EntidadHijaRepository entidadHijaService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;
    
    
    /**
     * Ensures that the Spring application context loads successfully when the main method is called.
     */
    @Test
    @Order(1)
    void contextLoads() {
        log.info("Iniciando la prueba de contextos...");
        assertThat(dbContainer.isRunning()).isTrue();
        assertThat(entidadHijaService.findAll()).isEmpty();
        assertThat(entidadPadreService.findAll()).isEmpty();
        for (String s : Arrays.asList("Context loaded", "Database name: " + dbContainer.getDatabaseName(), "Username: " + dbContainer.getUsername(), "Password: " + dbContainer.getPassword())) {
            log.info(s);
        }
        log.info("Contexto de Spring cargado con éxito.");
    }

    @Test
    @Order(2)
    void seCrean100EntidadesPadreEHija() {
        log.info("Iniciando inserción de datos de prueba... ");
        int numeroEntidades = 100;
        EntidadPadre[] entidades = new EntidadPadre[numeroEntidades];
        Arrays.setAll(entidades, i -> new EntidadPadre("Entidad-" + (Integer.valueOf(i) + 1)));
        entidadPadreService.saveAll(Arrays.asList(entidades));
        for (EntidadPadre entidadPadre : entidades) {
            EntidadHija entidadHija = new EntidadHija("Hija de " + entidadPadre.getNombre());
            entidadHija.setEntidadPadre(entidadPadre);
            entidadHijaService.save(entidadHija);
        }
        assertThat(entidadPadreService.findAll()).hasSize(100);
        assertThat(entidadHijaService.findAll()).hasSize(100);
        log.info("Se verificó que hay 100 entidades padre y 100 entidades hija.");
    }


    @Test
    @Order(3)
    void verificarEntidadesEnTemplates() throws Exception {
        log.info("Verificando datos en el template Thymeleaf...");

        // Insert relevant test entities
        EntidadPadre entidadPadre1 = new EntidadPadre("Padre Template 1");
        EntidadPadre entidadPadre2 = new EntidadPadre("Padre Template 2");
        entidadPadreService.saveAll(Arrays.asList(entidadPadre1, entidadPadre2));

        EntidadHija entidadHija1 = new EntidadHija("Hija Template 1");
        entidadHija1.setEntidadPadre(entidadPadre1);
        entidadHijaService.save(entidadHija1);

        EntidadHija entidadHija2 = new EntidadHija("Hija Template 2");
        entidadHija2.setEntidadPadre(entidadPadre2);
        entidadHijaService.save(entidadHija2);

        // Call the Thymeleaf-rendered endpoint using MockMvc
        String response = mockMvc.perform(get("/entities"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Verify that the model contains the expected entities
        assertThat(response).contains("Hija Template 1").contains("Hija Template 2");


        // Call the Thymeleaf-rendered endpoint using MockMvc
        response = mockMvc.perform(get("/protected").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("user:password".getBytes())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Also validate that the response contains the parent object details
        assertThat(response).contains("Padre Template 1").contains("Padre Template 2");

        log.info("Datos del modelo renderizados correctamente en el template de hijas y padres.");
    }

    @Test
    @Order(4)
    void insertarYBuscarEntidadPadrePorNombre() {
        log.info("Insertando y buscando una EntidadPadre por nombre...");
        EntidadPadre entidadPadre = new EntidadPadre("Padre de Prueba");
        entidadPadreService.save(entidadPadre);
        EntidadPadre encontrada = entidadPadreService.findByNombre("Padre de Prueba").orElse(null);
        assertThat(encontrada).isNotNull();
        assertThat(encontrada.getNombre()).isEqualTo("Padre de Prueba");
        log.info("EntidadPadre encontrada correctamente por nombre.");
    }

    @Test
    @Order(5)
    void EntidadesPadreFetchTypeEager() {
        log.info("Buscando entidades padre junto con sus entidades hija asociadas...");
        EntidadPadre entidadPadre = new EntidadPadre("Padre con Hijas");
        entidadPadreService.save(entidadPadre);

        EntidadHija entidadHija1 = new EntidadHija("Hija 1");
        entidadHija1.setEntidadPadre(entidadPadre);
        entidadHijaService.save(entidadHija1);

        EntidadHija entidadHija2 = new EntidadHija("Hija 2");
        entidadHija2.setEntidadPadre(entidadPadre);
        entidadHijaService.save(entidadHija2);

        EntidadPadre padreConHijas = entidadPadreService.findByNombre("Padre con Hijas").orElse(null);

        assertThat(padreConHijas).isNotNull();
        assertThat(padreConHijas.getEntidadesHijas()).hasSize(2);
        log.info("Se verificó que las entidades hijo están correctamente asociadas con la entidad padre.");
    }

    @Test
    @Order(6)
    void testDeOrphanRemoval() {
        log.info("Eliminando una entidad padre específica y verificando la cascada...");
        EntidadPadre entidadPadre = new EntidadPadre("Padre a Eliminar");
        entidadPadre = entidadPadreService.save(entidadPadre);

        EntidadHija entidadHija = new EntidadHija("Hija del Padre a Eliminar");
        entidadHija.setEntidadPadre(entidadPadre);
        entidadHijaService.save(entidadHija);

        entidadPadreService.delete(entidadPadre);

        assertThat(entidadPadreService.findAll().stream().noneMatch(ep -> ep.getNombre().equals("Padre a Eliminar"))).isTrue();
        assertThat(entidadHijaService.findAll().stream().noneMatch(eh -> eh.getNombre().equals("Hija del Padre a Eliminar"))).isTrue();
        log.info("Se verificó la eliminación en cascada de las entidades hijo al eliminar la entidad padre.");
    }

    @Test
    @Order(7)
    void actualizarEntidadPadre() {
        log.info("Actualizando una entidad padre y verificando sus detalles persistidos...");
        EntidadPadre entidadPadre = new EntidadPadre("Padre Actualizar");
        entidadPadreService.save(entidadPadre);

        entidadPadre.setNombre("Padre Actualizado");
        entidadPadreService.save(entidadPadre);

        EntidadPadre entidadActualizada = entidadPadreService.findByNombre("Padre Actualizado").orElse(null);

        assertThat(entidadActualizada).isNotNull();
        assertThat(entidadActualizada.getNombre()).isEqualTo("Padre Actualizado");
        log.info("Entidad padre actualizada correctamente.");
    }
    

    @Test
    @Order(8)
    void actualizarNombreEntidadHijaMantieneReferenciaPadre() {
        log.info("Verificando que actualizar el nombre de una entidad hija mantiene la referencia de su padre...");
        EntidadPadre entidadPadre = new EntidadPadre("Padre de Hija a Actualizar");
        entidadPadreService.save(entidadPadre);

        EntidadHija entidadHija = new EntidadHija("Hija Original");
        entidadHija.setEntidadPadre(entidadPadre);
        entidadHijaService.save(entidadHija);

        entidadHija.setNombre("Hija Actualizada");
        entidadHijaService.save(entidadHija);

        EntidadHija entidadHijaActualizada = (EntidadHija) entidadHijaService.findByNombre("Hija Actualizada").orElse(null);

        assertThat(entidadHijaActualizada).isNotNull();
        assertThat(entidadHijaActualizada.getEntidadPadre().getNombre()).isEqualTo("Padre de Hija a Actualizar");
        log.info("La actualización del nombre de la hija no afectó la referencia al padre.");
    }


    @Test
    @Order(9)
    void eliminarTodasLasEntidadesAntesDeProbar() {
        log.info("Eliminando todas las entidades antes de ejecutar las pruebas siguientes...");
        entidadHijaService.deleteAll();
        entidadPadreService.deleteAll();
        assertThat(entidadHijaService.findAll()).isEmpty();
        assertThat(entidadPadreService.findAll()).isEmpty();
        log.info("Todas las entidades fueron eliminadas correctamente.");
    }

    @Test
    @Order(10)
    void buscarEntidadesPadrePorPatronDeNombre() {


        log.info("Buscando entidades padre por patrón parcial en el nombre...");
        EntidadPadre entidad1 = new EntidadPadre("Padre Uno");
        EntidadPadre entidad2 = new EntidadPadre("Padre Dos");
        entidadPadreService.saveAll(Arrays.asList(entidad1, entidad2));

        assertThat(entidadPadreService.findByNombreContaining("Padre")).hasSize(2);
        log.info("Se encontraron todas las entidades padre por el patrón correctamente.");
    }

    @Test
    @Order(10)
    void agregarMultiplesEntidadesHijasAUnPadre() {
        log.info("Agregando múltiples entidades hija a un único padre...");
        EntidadPadre entidadPadre = new EntidadPadre("Padre con Varias Hijas");
        entidadPadreService.save(entidadPadre);

        EntidadHija hija1 = new EntidadHija("Hija 1");
        EntidadHija hija2 = new EntidadHija("Hija 2");
        EntidadHija hija3 = new EntidadHija("Hija 3");
        hija1.setEntidadPadre(entidadPadre);
        hija2.setEntidadPadre(entidadPadre);
        hija3.setEntidadPadre(entidadPadre);

        entidadHijaService.saveAll(Arrays.asList(hija1, hija2, hija3));

        EntidadPadre entidadPadreConHijas = entidadPadreService.findByNombre("Padre con Varias Hijas").orElse(null);

        assertThat(entidadPadreConHijas).isNotNull();
        assertThat(entidadPadreConHijas.getEntidadesHijas()).hasSize(3);
        log.info("Se verificó que un padre puede tener múltiples entidades hija correctamente.");
    }


    @Test
    @Order(11)
    void verificarEntidadesEnThymeleaf() {
        log.info("Verificando que las entidades insertadas se renderizan correctamente en Thymeleaf...");

        // Insert test entities
        EntidadPadre entidadPadre = new EntidadPadre("Padre Thymeleaf");
        entidadPadreService.save(entidadPadre);

        EntidadHija entidadHija = new EntidadHija("Hija Thymeleaf");
        entidadHija.setEntidadPadre(entidadPadre);
        entidadHijaService.save(entidadHija);


        // Call the Thymeleaf-rendered endpoint to get all entities
        String responseEntities = this.restTemplate.getForObject("/entities", String.class);

        // Verify that the response contains the expected list of entities
        assertThat(responseEntities).contains("Hija Thymeleaf");
        log.info("Datos renderizados en Thymeleaf correctamente verificados.");
    }


    
    
}
