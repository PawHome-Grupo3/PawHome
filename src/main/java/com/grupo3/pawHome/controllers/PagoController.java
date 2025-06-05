package com.grupo3.pawHome.controllers;
import com.grupo3.pawHome.repositories.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagoController {

    private final MetodoPagoRepository metodoPagoRepo;

    public PagoController(MetodoPagoRepository metodoPagoRepo) {
        this.metodoPagoRepo = metodoPagoRepo;
    }

    @GetMapping("/donar")
    public String mostrarDonacion(Model model) {
        model.addAttribute("donacionesMonetarias", metodoPagoRepo.findByTipoPago_Nombre("MONETARIO"));
        model.addAttribute("donacionesMateriales", metodoPagoRepo.findByTipoPago_Nombre("MATERIAL"));
        return "dona";
    }

    @GetMapping("/apadrinar")
    public String mostrarApadrinamiento(Model model) {
        model.addAttribute("metodosApadrinar", metodoPagoRepo.findByTipoPago_Nombre("APADRINAMIENTO"));
        return "apadrinar";
    }

    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
        model.addAttribute("metodosTienda", metodoPagoRepo.findByTipoPago_Nombre("TIENDA"));
        return "tienda";
    }

    @GetMapping("/adoptar")
    public String mostrarAdopcion(Model model) {
        model.addAttribute("metodosAdopcion", metodoPagoRepo.findByTipoPago_Nombre("ADOPCION"));
        return "adoptar";
    }
}
