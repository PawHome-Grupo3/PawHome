package com.grupo3.pawHome.controllers;
import com.grupo3.pawHome.repositories.MetodoPagoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagoController {

    private final MetodoPagoRepository metodoPagoRepo;

    public PagoController(MetodoPagoRepository metodoPagoRepo) {
        this.metodoPagoRepo = metodoPagoRepo;
    }

    @GetMapping("/pagos")
    public String mostrarPagos(Model model) {
        model.addAttribute("donacionesMonetarias", metodoPagoRepo.findByTipoPago_Nombre("MONETARIO"));
        model.addAttribute("donacionesMateriales", metodoPagoRepo.findByTipoPago_Nombre("MATERIAL"));
        return "pagos";
    }

    @GetMapping("/pago-correcto")
    public String mostrarSuccess() { return "pagoCorrecto"; }

    @GetMapping("/pago-incorrecto")
    public String mostrarCancel() { return "cancel"; }
}