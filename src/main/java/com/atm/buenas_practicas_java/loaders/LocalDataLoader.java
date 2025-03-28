package com.atm.buenas_practicas_java.loaders;

import com.atm.buenas_practicas_java.entities.EntidadHija;
import com.atm.buenas_practicas_java.entities.EntidadPadre;
import com.atm.buenas_practicas_java.repositories.EntidadHijaRepository;
import com.atm.buenas_practicas_java.repositories.EntidadPadreRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;


/**
 * Clase de configuración que se utiliza exclusivamente para el perfil "default" en entornos locales.
 *
 * Su propósito principal es cargar datos de ejemplo en las bases de datos asociadas a las entidades
 * {@code EntidadPadre} y {@code EntidadHija}, permitiendo la inicialización de datos útiles para pruebas
 * y desarrollo en este perfil específico.
 *
 * Esta clase está anotada con:
 * - {@code @Configuration}: Define esta clase como fuente de beans y configuración.
 * - {@code @Log4j2}: Habilita el uso de la biblioteca Log4j2 para registro de mensajes en los logs.
 * - {@code @Profile("default")}: Asegura que esta clase solo se cargue en el perfil "default".
 *
 * @see EntidadPadreRepository
 * @see EntidadHijaRepository
 */
@Configuration
@Log4j2
@Profile("local")
public class LocalDataLoader {

    private final EntidadPadreRepository repository;
    private final EntidadHijaRepository entidadHijaRepository;

    /**
     * Constructor de la clase {@code LocalDataLoader}.
     *
     * Inicializa un objeto {@code LocalDataLoader} configurado con los repositorios de las entidades,
     * proporcionando la capacidad de interactuar con estas entidades en la base de datos.
     *
     * @param repository              El repositorio de la entidad padre {@code EntidadPadreRepository}.
     *                                Se utiliza para realizar operaciones de persistencia, actualización,
     *                                eliminación y consulta relacionadas con la entidad padre.
     * @param entidadHijaRepository   El repositorio de la entidad hija {@code EntidadHijaRepository}.
     *                                Es utilizado para gestionar datos de la entidad hija y su relación con
     *                                la entidad padre.
     */
    public LocalDataLoader(EntidadPadreRepository repository, EntidadHijaRepository entidadHijaRepository) {
        this.repository = repository;
        this.entidadHijaRepository = entidadHijaRepository;
    }

    /**
     * Método anotado con {@code @PostConstruct} que inicializa datos de prueba en
     * los repositorios para entornos locales. Este método se ejecuta automáticamente
     * después de la inicialización del bean y antes de que esté disponible para uso,
     * permitiendo cargar datos iniciales necesarios para el perfil local.
     *
     * Funcionalidad:
     * - Crea 10 instancias de la entidad `EntidadPadre` con nombres predefinidos.
     * - Guarda las instancias de `EntidadPadre` en el repositorio correspondiente.
     * - Para cada instancia de `EntidadPadre`, crea una entidad relacionada de tipo
     *   `EntidadHija` con un nombre identificativo, y la asocia a la entidad padre
     *   pertinente.
     * - Guarda las entidades hijas en el repositorio `entidadHijaRepository`.
     * - Registra mensajes informativos en el log sobre el inicio y finalización del proceso.
     *
     * Proceso:
     * 1. Se define un número fijo de entidades padre (10).
     * 2. Se utiliza un array para almacenar las instancias y se inicializa con un nombre
     *    único para cada entidad padre.
     * 3. Todas las entidades padre se guardan de forma simultánea utilizando
     *    {@code repository.saveAll}.
     * 4. Para cada entidad padre, se crea una instancia de la entidad hija, se establece
     *    la relación con el padre y se guarda en el repositorio correspondiente.
     * 5. Se registran logs informativos sobre el estado del proceso.
     *
     * Dependencias principales:
     * - `repository`: {@code EntidadPadreRepository}, usado para almacenar las entidades padre.
     * - `entidadHijaRepository`: {@code EntidadHijaRepository}, usado para guardar las entidades hijas.
     *
     * Importante:
     * - Este método está diseñado específicamente para ser utilizado en entornos con
     *   el perfil local activo.
     * - No debe usarse en entornos de producción, ya que sobrescribirá datos existentes.
     *
     * Logs:
     * - Mensaje al inicio del proceso: "Iniciando la carga de datos para el perfil local".
     * - Mensaje exitoso al finalizar: "Datos de entidades cargados correctamente."
     */
    @PostConstruct
    public void loadDataLocal() {

        log.info("Iniciando la carga de datos para el perfil local");
        int numeroEntidades = 100;
        EntidadPadre[] entidades = new EntidadPadre[numeroEntidades];
        Arrays.setAll(entidades, i -> new EntidadPadre("Entidad-" + (Integer.valueOf(i)+1)));
        repository.saveAll(Arrays.asList(entidades));
        for (EntidadPadre entidadPadre : entidades) {
            EntidadHija entidadHija = new EntidadHija("Hija de " + entidadPadre.getNombre());
            entidadHija.setEntidadPadre(entidadPadre);
            entidadHijaRepository.save(entidadHija);
        }
        log.info("Datos de entidades cargados correctamente.");
    }





}
