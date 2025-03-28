package com.atm.buenas_practicas_java.loaders;

import com.atm.buenas_practicas_java.repositories.EntidadHijaRepository;
import com.atm.buenas_practicas_java.repositories.EntidadPadreRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

    /**
 * Clase de configuración que permite cargar datos iniciales en los repositorios
 * de entidades para diferentes perfiles de configuración.
 *
 * Esta clase es útil para inicializar datos predefinidos utilizados durante el
 * desarrollo o en entornos locales.
 *
 * Se utiliza la anotación @Configuration para indicar que es una clase de configuración
 * de Spring, y métodos específicos anotados con @Profile para definir qué datos
 * iniciales se cargarán según el perfil activo.
 */


@Configuration
@Log4j2
@Profile("produccion")
public class ProductionlDataLoader {

    private final EntidadPadreRepository repository;
    private final EntidadHijaRepository entidadHijaRepository;
    /**
     * Clase de configuración que permite cargar datos iniciales en los repositorios
     * de entidades para diferentes perfiles de configuración.
     *
     * Esta clase es útil para inicializar datos predefinidos utilizados durante el
     * desarrollo o en entornos específicos según el perfil.
     *
     * **Anotaciones utilizadas**:
     * - `@Configuration`: Define esta clase como una clase de configuración de Spring.
     *   Permite registrar beans en el contexto de la aplicación y gestionar configuraciones específicas.
     *
     * - `@Log4j2`: Habilita el uso de Log4j2 para registrar mensajes de log importantes,
     *   utilizados para monitoreo y depuración de la aplicación.
     *
     * Cada método anotado con `@Profile` y `@PostConstruct` permite la carga de datos
     * iniciales dependiendo del perfil activo.
     */
    public ProductionlDataLoader(EntidadPadreRepository repository, EntidadHijaRepository entidadHijaRepository) {
        this.repository = repository;
        this.entidadHijaRepository = entidadHijaRepository;
    }

    @Profile("produccionRESETDATA")
    public void loadDataProduccion() {
        log.info("Iniciando la carga de datos para el perfil de producción.");
        log.info("Datos de producción aún no definidos. Este método requiere implementación adicional.");
        repository.deleteAll();
        entidadHijaRepository.deleteAll();
        log.info("Datos de entidades borrados correctamente.");
    }



}
