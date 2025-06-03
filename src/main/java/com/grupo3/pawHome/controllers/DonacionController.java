/* Controlador para metodos de pagos en donacion */

package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.repositories.MetodoDonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DonacionController {

    @Autowired
    private MetodoDonacionRepository donacionRepo;

    @GetMapping("/donar")
    public String mostrarDonacion(Model model) {
        model.addAttribute("donacionesMonetarias", donacionRepo.findByTipo("monetario"));
        model.addAttribute("donacionesMateriales", donacionRepo.findByTipo("material"));
        return "dona";
    }
}
