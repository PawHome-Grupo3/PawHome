package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.grupo3.pawHome.entities.*;
import com.grupo3.pawHome.services.*;
import com.grupo3.pawHome.util.SessionUtils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductCheckoutController {

    private final StripeService stripeService;
    private final PagoService pagoService;
    private final FacturaService facturaService;
    private final TallaService tallaService;

    public ProductCheckoutController(StripeService stripeService,
                                     PagoService pagoService,
                                     FacturaService facturaService,
                                     TallaService tallaService) {
        this.stripeService = stripeService;
        this.pagoService = pagoService;
        this.facturaService = facturaService;
        this.tallaService = tallaService;
    }

    @PostMapping("/product/v1/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody List<ProductRequest> productRequests) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequests);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

    @GetMapping("/success")
    public RedirectView procesarPagoExitoso(@RequestParam("session_id") String sessionId,
                                            @AuthenticationPrincipal Usuario usuario,
                                            HttpSession session) throws StripeException {

        List<ItemCarritoDTO> carrito = SessionUtils.obtenerCarritoSeguro(session);

        if (usuario == null || carrito.isEmpty()) { return new RedirectView("/cancel"); }

        Factura factura = new Factura();
        factura.setUsuario(usuario);
        factura.setFecha(LocalDate.now());

        String descripcion = (session.getAttribute("motivo") == null)? "" : session.getAttribute("motivo").toString();
        factura.setDescripcion(descripcion);

        if(descripcion.equals("Compra en Tienda")){
            for (ItemCarritoDTO item : carrito) {
                Optional<Talla> talla = tallaService.findById(item.getTalla().getId());
                if (talla.isPresent()) {
                    Talla t = talla.get();
                    t.setStock(t.getStock() - item.getCantidad());
                    tallaService.save(t);
                }
            }
        }

        double total = carrito.stream()
                .mapToDouble(item -> item.getPrecioUnitario() * item.getCantidad())
                .sum();
        factura.setPrecio(total);

        List<LineaFactura> lineas = carrito.stream().map(item -> {
            LineaFactura linea = new LineaFactura();
            linea.setNombre(item.getProducto().getNombre());
            linea.setCantidad(item.getCantidad());
            linea.setDescripcion(item.getProducto().getDescripcion());
            linea.setTarifa(item.getTarifa());
            linea.setFactura(factura);
            return linea;
        }).toList();

        factura.setLineaFacturas(lineas);
        factura.setUsuario(usuario);
        facturaService.save(factura);

        Stripe.apiKey = "sk_test_51RWyBgRXrrBHAtjR0rBoi8CYT9zyaKncktYEV95cpRMfObDpDxcJCkyUaGuWMomwyHEVn6EMSr1chZiHAusAn3sV00lsOuxBfI";
        Session sessionStripe = Session.retrieve(sessionId);

        String paymentIntentId = sessionStripe.getPaymentIntent();

        Pago pago = new Pago();
        pago.setEstado(true);
        pago.setAutorizacion(paymentIntentId);
        pago.setFactura(factura);
        pago.setUsuario(usuario);

        pagoService.save(pago);

        session.removeAttribute("carrito");

        return new RedirectView("/pago-correcto");
    }

    @GetMapping("/cancel")
    public RedirectView procesarPagoIncorrecto(){

        return new RedirectView("/pago-incorrecto");
    }
}

