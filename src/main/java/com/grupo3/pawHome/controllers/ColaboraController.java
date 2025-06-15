package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.dtos.FormAdoptaDTO;
import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.grupo3.pawHome.entities.Animal;
import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.AnimalService;
import com.grupo3.pawHome.services.StripeService;
import com.grupo3.pawHome.services.UsuarioService;
import com.grupo3.pawHome.util.SecurityUtil;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ColaboraController {

    private final UsuarioService usuarioService;
    private final StripeService stripeService;
    private final SecurityUtil securityUtil;
    private final AnimalService animalService;

    public ColaboraController(UsuarioService usuarioService, StripeService stripeService, SecurityUtil securityUtil, AnimalService animalService) {
        this.usuarioService = usuarioService;
        this.stripeService = stripeService;
        this.securityUtil = securityUtil;
        this.animalService = animalService;
    }

    @GetMapping("/colabora/donaciones-materiales")
    public String mostrarColaboraDonaMateriales()
    {
        return "donarMateriales";
    }

    @GetMapping("/colabora/dona")
    public String mostrarColaboraDona(){ return "Dona"; }

    @PostMapping("/colabora/dona/checkout")
    public ResponseEntity<StripeResponse> checkoutDesdeDona(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestBody Map<String, Object> datos,
            HttpSession session
    ) throws StripeException {

        if (userDetails == null) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Usuario no autenticado. Por favor inicia sesión")
                            .build()
            );
        }

        Usuario usuario = userDetails.getUsuario();
        if (usuario.getPerfilDatos() == null) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Datos de Perfil Incompletos")
                            .build()
                    );
        }

        double precio = Double.parseDouble(datos.get("cantidad").toString());
        long precioRequest = (long) (precio * 100);

        ProductRequest productRequest = new ProductRequest(precioRequest, (long)1, "donacion", "eur");
        List<ProductRequest> productRequests = new ArrayList<>();
        productRequests.add(productRequest);

        session.setAttribute("precio", precio);

        Usuario userCustomerId = usuarioService.ensureStripeCustomerExists(usuario);
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequests, userCustomerId.getStripeCustomerId());
        securityUtil.updateAuthenticatedUser(userCustomerId);

        session.setAttribute("motivo", "Donacion");

        return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
    }

    @GetMapping("/colabora/adopta/formularioAdopta")
    public String mostrarColaboraDona(@AuthenticationPrincipal MyUserDetails userDetails,
                                      Model model,
                                      @RequestParam(required = false) Integer animalId) {
        if (userDetails != null) {
            Usuario usuario = userDetails.getUsuario();
            model.addAttribute("usuario", usuario);

            if(usuario.getPerfilDatos() != null){
                PerfilDatos perfilDatos = usuario.getPerfilDatos();

                FormAdoptaDTO formAdoptaDTO = new FormAdoptaDTO(usuario.getId(), perfilDatos.getNombre(),
                        perfilDatos.getDni(), perfilDatos.getEdad(), perfilDatos.getTelefono1(),
                        usuario.getEmail(), perfilDatos.getDireccion());

                if(animalId != null) {
                    Optional<Animal> animalOpt = animalService.findById(animalId);
                    formAdoptaDTO.setAnimal(animalOpt.orElse(null));
                    model.addAttribute("animal", animalOpt.orElse(null));
                }

                model.addAttribute("formAdoptaDTO", formAdoptaDTO);
            }
        }
        else{
            model.addAttribute("formAdoptaDTO", new FormAdoptaDTO());
        }

        return "formularioAdopta";
    }

    @PostMapping("/colabora/adopta/crearFormulario")
    public String procesarFormularioAdopta(
            @Valid @ModelAttribute("formAdoptaDTO") FormAdoptaDTO formAdoptaDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttrs) {

        // Validaciones: si hay errores, volvemos al formulario
        if (bindingResult.hasErrors()) {
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.formAdoptaDTO", bindingResult);
            redirectAttrs.addFlashAttribute("formAdoptaDTO", formAdoptaDTO);
            return "redirect:/colabora/adopta/formularioAdopta";
        }



        // Aquí procesarías el DTO, por ejemplo guardarlo en base de datos o llamar a servicio
        model.addAttribute("mensajeExito", "Formulario enviado correctamente");

        return "redirect:/";
    }
}
