package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.entities.*;
import com.grupo3.pawHome.services.*;
import com.grupo3.pawHome.util.SessionUtils;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.stripe.exception.StripeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/apadrinar")
public class ApadrinarController {

    private final AnimalService animalService;
    private final ApadrinarService apadrinarService;
    private final PagoService pagoService;
    private final FacturaService facturaService;

    public ApadrinarController(AnimalService animalService,
                               ApadrinarService apadrinarService,
                               PagoService pagoService,
                               FacturaService facturaService) {

        this.animalService = animalService;
        this.apadrinarService = apadrinarService;
        this.pagoService = pagoService;
        this.facturaService = facturaService;
    }

    @GetMapping("/success")
    public String procesarExito(@RequestParam("session_id") String sessionId,
                                @AuthenticationPrincipal MyUserDetails userDetails,
                                Model model,
                                HttpSession httpSession) throws StripeException {

        Usuario usuario = userDetails.getUsuario();
        Session session = Session.retrieve(sessionId);

        String subscriptionId = session.getSubscription();
        Subscription subscription = Subscription.retrieve(subscriptionId);

        Animal animalSession = SessionUtils.obtenerAnimalSeguro(httpSession);
        Animal animal = animalService.findById(animalSession.getId())
                .orElseThrow(() -> new IllegalArgumentException("Animal no encontrado"));

        String aporteMensualStr = subscription.getMetadata().get("aporteMensual");
        double aporteMensual = (aporteMensualStr != null ? Double.parseDouble(aporteMensualStr) : 0.0)/100;

        Factura factura = new Factura();
        factura.setFecha(LocalDate.now());
        factura.setUsuario(usuario);
        factura.setDescripcion("Apadrinamiento Mensual: " + aporteMensual + "€ del animal "
                + animal.getNombre() + " con chip: " + animal.getChip());

        LineaFactura lineaFactura = new LineaFactura();
        lineaFactura.setCantidad(1);
        lineaFactura.setNombre(animal.getNombre());
        lineaFactura.setDescripcion(aporteMensual + "€");
        lineaFactura.setFactura(factura);

        List<LineaFactura> lineaFacturaList = new ArrayList<>();
        lineaFacturaList.add(lineaFactura);

        factura.setLineaFacturas(lineaFacturaList);
        facturaService.save(factura);

        Apadrinar a = new Apadrinar();
        a.setUsuario(usuario);
        a.setAnimal(animal);
        a.setAporteMensual(aporteMensual);
        a.setFechaInicio(LocalDate.now());
        a.setStripeSubscriptionId(subscription.getId());
        a.setFechaRenovacion(LocalDate.now().plusMonths(1));
        apadrinarService.save(a);

        Pago pago = new Pago();
        pago.setEstado(true);
        pago.setAutorizacion(subscriptionId);
        pago.setFactura(factura);
        pago.setUsuario(usuario);
        pagoService.save(pago);

        model.addAttribute("animal", animal);
        return "apadrinamientoExitoso";
    }
}

