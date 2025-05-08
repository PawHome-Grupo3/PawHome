package com.grupo3.pawHome.controllers;


import com.grupo3.pawHome.services.EntidadHijaService;
import com.grupo3.pawHome.services.EntidadPadreService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 * Controlador encargado de manejar las solicitudes relacionadas con la entidad principal.
 *
 * Este controlador utiliza la anotación {@code @Controller} para ser detectado como un componente
 * Spring MVC y maneja solicitudes HTTP. Su objetivo principal es gestionar las operaciones
 * necesarias para mostrar una lista de entidades en la vista correspondiente.
 *
 * Anotaciones importantes:
 * - {@code @Controller}: Indica que esta clase se comporta como un controlador Spring MVC.
 * - {@code @PreAuthorize}: Define que el acceso a ciertos métodos esté restringido
 *   según las reglas de autorización establecidas.
 *
 * Dependencias:
 * - {@code EntidadPadreRepository}: Interfaz del repositorio que permite interactuar con
 *   la base de datos para operaciones de persistencia y consulta relacionadas con
 *   la entidad padre.
 *
 * Métodos principales:
 * - {@code listEntities}: Maneja solicitudes GET a la URL "/entities", recupera los
 *   datos de las entidades desde la base de datos y los pasa al modelo para mostrarlos
 *   en una vista.
 *
 */
@Controller
public class DefaultController {

    private final EntidadHijaService entidadHijaService;
    private final EntidadPadreService entidadPadreService;

    /**
     * Constructor de la clase DefaultController.
     * <p>
     * Inicializa el controlador principal asignando los servicios
     * utilizados para gestionar las entidades EntidadPadre y EntidadHija.
     *
     * @param entidadHijaService  instancia de {@link EntidadHijaService} que proporciona
     *                            funcionalidades adicionales relacionadas con la entidad EntidadHija.
     * @param entidadPadreService instancia de {@link EntidadPadreService} que proporciona
     *                            funcionalidades adicionales relacionadas con la entidad EntidadPadre.
     */
    public DefaultController(EntidadHijaService entidadHijaService, EntidadPadreService entidadPadreService) {
        this.entidadHijaService = entidadHijaService;
        this.entidadPadreService = entidadPadreService;
    }

    /**
     * Método que lista las entidades disponibles y las añade al modelo para ser utilizadas en la vista.
     * Recupera todas las entidades de un repositorio y las presenta en una vista específica.
     *
     * @param model El objeto del modelo que se utiliza para compartir datos entre el backend y la vista.
     *              Aquí se añade un atributo llamado "entities" con la lista obtenida del repositorio.
     * @return Una cadena que representa el nombre de la vista ("entitiesList") donde se renderizarán las entidades.
     */
    @GetMapping("/entities")
    public String listEntities(Model model)
    {
        model.addAttribute("entidades", entidadHijaService.findAll());
        return "entidadesHijas"; // View name
    }

    // Metodo para mostrar pagina de inicio
    @GetMapping("")
    public String mostrarIndex(Model model)
    {
        model.addAttribute("currentLocale", LocaleContextHolder.getLocale().getDisplayName());
        return "index"; // Carga /templates/index.html
    }

    @GetMapping("/detalleProducto")
    public String mostrarDetalleProducto()
    {
        return "detalleProducto"; // Carga /templates/index.html
    }

    @GetMapping("/listaCarrito")
    public String mostrarListaCarrito() { return "listaCarrito"; }

    // Metodo para mostrar pagina de inicio
    @GetMapping("/tienda")
    public String mostrarTienda()
    {
        return "tienda"; // Carga /templates/tienda.html
    }

    // Metodo para mostrar la pagina de la guarderia
    @GetMapping("/guarderia")
    public String mostrarGuarderia()
    {
        return "guarderia";
    }

    // Metodo para mostrar la pagina de la peluqueria
    @GetMapping("/peluqueria")
    public String mostrarPeluqueria()
    {
        return "peluqueria";
    }

    // Metodo para mostrar la pagina de adiestramiento
    @GetMapping("/adiestramiento")
    public String mostrarAdiestramiento()
    {
        return "adiestramiento";
    }

    // Metodo para mostrar la pagina de veterinario
    @GetMapping("/veterinario")
    public String mostrarVeterinario()
    {
        return "veterinario";
    }

    // Metodo para mostrar la pagina de asesoramiento legal
    @GetMapping("/asesoramientoLegal")
    public String mostrarAsesoramientoLegal()
    {
        return "asesoramientoLegal";
    }

    // Metodo para mostrar la pagina de nuestros animales
    @GetMapping("/nuestrosAnimales")
    public String mostrarNuestrosAnimales()
    {
        return "nuestrosAnimales";
    }

    // Metodo para mostrar la pagina de contacto
    @GetMapping("/contacto")
    public String mostrarContacto()
    {
        return "contacto";
    }

    // Metodo para mostrar la pagina del buzón de sugerencias
    @GetMapping("/buzonsugerencias")
    public String mostrarBuzonSugerencias()
    {
        return "buzonsugerencias";
    }

    // Metodo para mostrar la pagina del horario y el mapa
    @GetMapping("/horariomapa")
    public String mostrarHorarioMapa()
    {
        return "horariomapa";
    }

    // Metodo para mostrar la pagina del horario y el mapa
    @GetMapping("/perfil/informacion")
    public String mostrarPerfil()
    {
        return "perfilUsuario";
    }

    @GetMapping("/perfil/editar")
    public String mostrarPerfilEditar()
    {
        return "perfilUsuarioEditar";
    }

    @GetMapping("/perfil/puntos")
    public String mostrarPerfilPuntos()
    { return "perfilUsuarioPuntos"; }

    @GetMapping("/perfil/animales")
    public String mostrarPerfilAnimales()
    {
        return "perfilUsuarioAnimales";
    }

    @GetMapping("/perfil/adopciones")
    public String mostrarPerfilAdopciones()
    {
        return "perfilUsuarioAdopciones";
    }

    @GetMapping("/perfil/donaciones")
    public String mostrarPerfilDonaciones()
    {
        return "perfilUsuarioDonaciones";
    }

    /**
     * Gestiona las solicitudes GET para obtener y mostrar la lista de entidades protegidas.
     * Añade las entidades obtenidas del repositorio al modelo para renderizarlas en la vista correspondiente.
     *
     * @param model Objeto {@link Model} que se utiliza para pasar datos desde el controlador a la vista.
     *              Contendrá la lista de entidades recuperadas desde el repositorio.
     * @return El nombre de la vista "entitiesList" donde se mostrará la lista de entidades.
     */
    @GetMapping("/protected")
    public String protectedList(Model model)
    {
        model.addAttribute("entidades", entidadPadreService.findAll());
        return "entidadesPadre"; // View name
    }

    /**
     * Deletes an EntidadHija entity by its ID using the EntidadHijaService.
     *
     * @param id The ID of the EntidadHija to delete.
     * @return A redirect to the "/protected" endpoint after deletion.
     */
    @PostMapping("/entidades/deleteHija/{id}")
    public String deleteEntidadHija(@PathVariable Long id) {
        entidadHijaService.deleteById(id);
        return "redirect:/entities";
    }

    /**
     * Deletes an EntidadHija entity by its ID using the EntidadHijaService.
     *
     * @param id The ID of the EntidadHija to delete.
     * @return A redirect to the "/protected" endpoint after deletion.
     */
    @PostMapping("/entidades/deletePadre/{id}")
    public String deleteEntidadPadre(@PathVariable Long id) {
        entidadPadreService.deleteById(id);
        return "redirect:/entities";
    }

}
