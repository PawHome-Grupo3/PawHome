package com.atm.buenas_practicas_java;
import com.atm.buenas_practicas_java.entities.EntidadHija;
import com.atm.buenas_practicas_java.entities.EntidadPadre;
import com.atm.buenas_practicas_java.repositories.EntidadHijaRepository;
import com.atm.buenas_practicas_java.repositories.EntidadPadreRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Clase de prueba unitaria para el repositorio de la entidad EntidadPadre.
 * Esta clase implementa varios métodos de prueba para garantizar la correcta funcionalidad
 * del repositorio y la persistencia de datos en una base de datos relacional, utilizando
 * Testcontainers para configurar un contenedor de base de datos MySQL.
 *
 * <p>Las pruebas incluidas aseguran el correcto comportamiento de las operaciones CRUD y las
 * relaciones entre entidades.
 *
 * <p><strong>Anotaciones utilizadas:</strong>
 * <ul>
 *   <li>{@code @Log4j2}:
 *       Proporcionada por Lombok, habilita el registro de logs mediante la API de Log4j2, permitiendo
 *       escribir mensajes de log fácilmente desde esta clase.</li>
 *   <li>{@code @DataJpaTest}:
 *       Una anotación de Spring Boot para configurar de manera automática un entorno de prueba centrado
 *       en JPA. Configura un contexto de prueba que incluye DataSource, EntityManager y todos los
 *       repositorios relacionados, pero sin cargar componentes web u otros servicios innecesarios.</li>
 *   <li>{@code @Testcontainers}:
 *       Específica de la biblioteca Testcontainers, habilita el uso de contenedores de Docker
 *       administrados para las pruebas, proporcionando un conjunto confiable de recursos externos
 *       como bases de datos o mensajes en colas para pruebas de integración.</li>
 *   <li>{@code @Autowired}:
 *       Una anotación de Spring que inyecta automáticamente las dependencias necesarias, en este
 *       caso, permite inyectar una instancia del repositorio EntidadPadreRepository.</li>
 *   <li>{@code @Container}:
 *       Proporcionada por Testcontainers, define un contenedor Docker que se inicializará
 *       automáticamente para pruebas. En este caso, se utiliza para configurar un contenedor MySQL.</li>
 *   <li>{@code @ServiceConnection}:
 *       Configuración específica de Spring Boot que permite obtener automáticamente la conexión
 *       configurada desde el contenedor Testcontainers para usarse en pruebas.</li>
 *   <li>{@code @Test}:
 *       Indicador de un método de prueba en JUnit. Permite marcar métodos que deben ejecutarse
 *       como casos de prueba.</li>
 *   <li>{@code @Order}:
 *       Anotación de JUnit 5 para especificar el orden secuencial de ejecución de los métodos
 *       de prueba dentro de la clase. Es útil en entornos donde el orden de las pruebas puede
 *       afectar resultados.</li>
 * </ul>
 */
@Log4j2
@SpringBootTest
class EntidadPadreRepositoryTest extends PostgreSQLContainerTest {

    @Autowired
    EntidadPadreRepository entidadPadreService;

    @Autowired
    EntidadHijaRepository entidadHijaService;


    @Autowired
    EntidadPadreRepository entidadPadreRepository;

    @Autowired
    EntidadHijaRepository entidadHijaRepository;


    /**
     * Prueba unitaria que verifica la funcionalidad de guardar una nueva entidad padre
     * en el repositorio asociado. Este método realiza lo siguiente:
     *
     * <ul>
     *   <li>Crea una instancia de la clase EntidadPadre.</li>
     *   <li>Asigna un valor a uno de sus campos, en este caso el nombre.</li>
     *   <li>Guarda la nueva instancia en el repositorio utilizando el método save().</li>
     *   <li>Recupera el identificador de la entidad guardada y verifica que no sea nulo,
     *       validando que el proceso de guardado fue exitoso.</li>
     * </ul>
     *
     * Este método garantiza que la persistencia de datos en el repositorio
     * funciona correctamente para la entidad padre.
     *
     * Anotaciones utilizadas:
     *
     * <ul>
     *   <li>{@code @Test}: Indica que este método es un caso de prueba que debe ser
     *       ejecutado por el framework de testing (habitualmente JUnit en el ecosistema Spring).</li>
     *   <li>{@code @Order(2)}: Especifica el orden de ejecución de este test en relación
     *       con otros en la misma clase. En este caso, el método se ejecutará en segundo lugar.</li>
     * </ul>
     *
     * Funcionalidad del método:
     * Este método asegura que el repositorio permite guardar la entidad correctamente,
     * asignándole un identificador único. También utiliza una aserción para garantizar
     * que la operación de persistencia no produce errores, validando que el ID generado
     * no sea nulo tras la operación.
     *
     */
    @Test
    @Order(2)
    void guardarEntidadPadre() {
        log.info("Iniciando la prueba guardarEntidadPadre...");
        var entidadPadre = new EntidadPadre();
        entidadPadre.setNombre("JetBrains");
        log.debug("Creada EntidadPadre con nombre {}", entidadPadre.getNombre());
        log.debug("Creada EntidadPadre con nombre {}", entidadPadre.getNombre());
        long entidadPadreId = entidadPadreRepository.save(entidadPadre).getId();
        log.debug("EntidadPadre guardada con ID {}", entidadPadreId);
        assertThat(entidadPadreId).isPositive();
    }

    /**
     * Método de prueba para verificar la funcionalidad de encontrar una entidad padre por su ID
     * utilizando el repositorio correspondiente.
     *
     * Este método evalúa los siguientes aspectos:
     * - La capacidad de guardar una entidad padre utilizando el repositorio.
     * - El correcto uso del método `findById` para recuperar una entidad basada en su identificador.
     * - La validación de los datos recuperados, asegurando que el nombre de la entidad guardada
     *   y encontrada coincida.
     *
     * Funcionamiento del método:
     * 1. Se crea una instancia de la clase `EntidadPadre`.
     * 2. Se asigna el nombre "JetBrains" a la entidad padre.
     * 3. Se guarda la entidad padre en la base de datos mediante el repositorio `entidadPadreRepository`.
     *    El ID generado al guardar la entidad se almacena.
     * 4. Se recupera la entidad padre de la base de datos utilizando el ID previamente guardado por
     *    medio del método `findById`.
     * 5. Se lanza una excepción si no se encuentra la entidad.
     * 6. Finalmente, se realiza una afirmación para verificar que el nombre de la entidad recuperada
     *    coincide con el nombre asignado inicialmente.
     *
     */
    @Test
    @Order(3)
    void encontrarEntidadPadreById() {
        log.debug("Iniciando la prueba encontrarEntidadPadreById...");
        var entidadPadre = new EntidadPadre();
        entidadPadre.setNombre("JetBrains");
        long entidadPadreId = entidadPadreRepository.save(entidadPadre).getId();
        log.debug("EntidadPadre guardada con ID {}", entidadPadreId);
        var entidadPadre1 = entidadPadreRepository.findById(entidadPadreId).orElseThrow();
        log.debug("EntidadPadre recuperada con ID {}", entidadPadre1.getId());
        assertThat(entidadPadre1.getNombre()).isEqualTo("JetBrains");
    }

    /**
     * Método de prueba para verificar la funcionalidad de búsqueda de una entidad padre
     * por su nombre en el repositorio correspondiente.
     *
     * Este método realiza lo siguiente:
     * 1. Crea una nueva instancia de `EntidadPadre` y establece su propiedad `nombre`.
     * 2. Guarda la entidad creada en el repositorio y obtiene su ID.
     * 3. Realiza la búsqueda de la entidad guardada por su nombre utilizando el método `findByNombre`.
     * 4. Verifica que el nombre de la entidad obtenida sea el mismo que el nombre almacenado,
     *    asegurando la consistencia de los datos.
     *
     * Este método ayuda a validar que las operaciones de consulta basadas en el nombre de la entidad
     * funcionan correctamente y que los datos recuperados están íntegros.
     *
     * Anotaciones:
     * - {@code @Test}: Indica que este método es un método de prueba de JUnit. Los métodos anotados
     *   con esta etiqueta serán ejecutados como pruebas.
     * - {@code @Order(4)}: Especifica el orden en el que este método de prueba debe ejecutarse en
     *   relación con otros métodos de prueba en la misma clase. En este caso, el método se ejecutará
     *   cuarto (4).
     */
    @Test
    @Order(4)
    void encontrarEntidadPadreByNombre() {
        log.debug("Iniciando la prueba encontrarEntidadPadreByNombre...");
        var entidadPadre = new EntidadPadre();
        entidadPadre.setNombre("JetBrains");
        entidadPadreRepository.save(entidadPadre);
        log.debug("Buscando EntidadPadre con nombre {}", entidadPadre.getNombre());
        EntidadPadre entidadPadre1 = entidadPadreRepository.findByNombre("JetBrains").orElseThrow();
        log.debug("EntidadPadre encontrada con nombre {}", entidadPadre1.getNombre());
        assertThat(entidadPadre1.getNombre()).isEqualTo("JetBrains");
    }


   
    /**
     * Prueba la creación de una entidad hija (EntidadHija) y su correcta asociación con una entidad padre (EntidadPadre).
     * Este método verifica que ambas entidades se persistan correctamente y que su relación se mantenga de forma adecuada.
     *
     * Pasos:
     * 1. Se crea e inicializa una nueva entidad padre (EntidadPadre).
     * 2. Se crea una nueva entidad hija (EntidadHija) y se asocia con la entidad padre.
     * 3. La entidad padre, junto con su entidad hija asociada, se persiste.
     * 4. Las afirmaciones aseguran que:
     *    - La entidad padre persistida no sea nula.
     *    - La entidad padre tenga exactamente una entidad hija.
     *    - El nombre de la entidad hija coincida con el valor esperado.
     *
     * La prueba registra pasos importantes y resultados para fines de validación y depuración.
     */
    @Test
    @Order(5)
    void crearEntidadHija() {

        log.debug("Iniciando la prueba crearEntidadHija...");
        var entidadPadre = new EntidadPadre("Padre1");
        var entidadHija = new EntidadHija();
        entidadHija.setNombre("Hija1");
        entidadHija.setEntidadPadre(entidadPadre);

        entidadPadre.setEntidadesHijas(List.of(entidadHija));
        var entidadPadreGuardada = entidadPadreRepository.save(entidadPadre);

        assertThat(entidadPadreGuardada).isNotNull();
        assertThat(entidadPadreGuardada.getEntidadesHijas()).hasSize(1);
        assertThat(entidadPadreGuardada.getEntidadesHijas().get(0).getNombre()).isEqualTo("Hija1");
        log.debug("EntidadPadre y EntidadHija guardadas correctamente con asociación válida.");
    }

}