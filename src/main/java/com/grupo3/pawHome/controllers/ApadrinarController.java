package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.config.StripeConfig;
import com.grupo3.pawHome.entities.*;
import com.grupo3.pawHome.services.*;
import com.grupo3.pawHome.util.SessionUtils;
import com.stripe.Stripe;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionListLineItemsParams;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.stripe.exception.StripeException;
import com.stripe.model.StripeCollection;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/apadrinar")
public class ApadrinarController {

    private final AnimalService animalService;
    private final ApadrinarService apadrinarService;
    private final PagoService pagoService;
    private final FacturaService facturaService;
    private final StripeConfig stripeConfig;

    public ApadrinarController(AnimalService animalService,
                               ApadrinarService apadrinarService,
                               PagoService pagoService,
                               FacturaService facturaService, StripeConfig stripeConfig) {

        this.animalService = animalService;
        this.apadrinarService = apadrinarService;
        this.pagoService = pagoService;
        this.facturaService = facturaService;
        this.stripeConfig = stripeConfig;
    }

    @GetMapping("/success")
    public String procesarExito(@RequestParam("session_id") String sessionId,
                                @AuthenticationPrincipal MyUserDetails userDetails,
                                Model model,
                                HttpSession httpSession) throws StripeException {

        Stripe.apiKey = stripeConfig.getSecretKey();

        Usuario usuario = userDetails.getUsuario();
        Session session = Session.retrieve(sessionId);

        String subscriptionId = session.getSubscription();
        Subscription subscription = Subscription.retrieve(subscriptionId);

        SessionListLineItemsParams params = SessionListLineItemsParams.builder()
                .setLimit(1L)
                .build();
        StripeCollection<LineItem> lineItems = session.listLineItems(params);
        String priceId = lineItems.getData().getFirst().getPrice().getId();

        Animal animalSession = SessionUtils.obtenerAnimalSeguro(httpSession);
        Animal animal = animalService.findById(animalSession.getId())
                .orElseThrow(() -> new IllegalArgumentException("Animal no encontrado"));

        String aporteMensualStr = subscription.getMetadata().get("aporteMensual");
        double aporteMensual = (aporteMensualStr != null ? Double.parseDouble(aporteMensualStr) : 0.0) / 100;

        Factura factura = new Factura();
        factura.setFecha(LocalDate.now());
        factura.setPrecio(aporteMensual);
        factura.setUsuario(usuario);
        factura.setDescripcion("Apadrinamiento Mensual: " + aporteMensual + "€ del animal "
                + animal.getNombre() + " con chip: " + animal.getChip());

        LineaFactura lineaFactura = new LineaFactura();
        lineaFactura.setCantidad(1);
        lineaFactura.setNombre(animal.getNombre());
        lineaFactura.setDescripcion(aporteMensual + "€");
        lineaFactura.setFactura(factura);

        factura.setLineaFacturas(List.of(lineaFactura));
        facturaService.save(factura);

        Apadrinar a = new Apadrinar();
        a.setUsuario(usuario);
        a.setAnimal(animal);
        a.setAporteMensual(aporteMensual);
        a.setFechaInicio(LocalDate.now());
        a.setFechaRenovacion(LocalDate.now().plusMonths(1));
        a.setStripeSubscriptionId(subscriptionId);
        a.setStripePriceId(priceId);
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

