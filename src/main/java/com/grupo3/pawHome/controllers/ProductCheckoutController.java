package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.grupo3.pawHome.entities.Factura;
import com.grupo3.pawHome.entities.LineaFactura;
import com.grupo3.pawHome.entities.Pago;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.FacturaService;
import com.grupo3.pawHome.services.PagoService;
import com.grupo3.pawHome.services.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ProductCheckoutController {

    private final StripeService stripeService;
    private final PagoService pagoService;
    private final FacturaService facturaService;

    public ProductCheckoutController(StripeService stripeService,
                                     PagoService pagoService,
                                     FacturaService facturaService) {
        this.stripeService = stripeService;
        this.pagoService = pagoService;
        this.facturaService = facturaService;
    }

    @PostMapping("/product/v1/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody List<ProductRequest> productRequests) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequests);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

    @GetMapping("/success")
    public String procesarPagoExitoso(@RequestParam("session_id") String sessionId, @AuthenticationPrincipal Usuario usuario, HttpSession session) throws StripeException {
        // 1. Recuperar el carrito y el pago de la sesión

        List<ItemCarritoDTO> carrito = (List<ItemCarritoDTO>) session.getAttribute("carritoCompra");
        Integer pagoId = (Integer) session.getAttribute("pagoId");

        if (pagoId == null || usuario == null) {
            return "error"; // página de error o redirección
        }

        Pago pago = pagoService.findById(pagoId).orElse(null);
        if (pago == null || pago.isEstado()) {
            return "error";
        }

        // 2. Crear la factura
        Factura factura = new Factura();
        factura.setUsuario(usuario);
        factura.setFecha(LocalDate.now());
        factura.setDescripcion("Compra en tienda");

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
        facturaService.save(factura);

        Stripe.apiKey = "sk_test_51RWyBgRXrrBHAtjR0rBoi8CYT9zyaKncktYEV95cpRMfObDpDxcJCkyUaGuWMomwyHEVn6EMSr1chZiHAusAn3sV00lsOuxBfI";
        Session sessionStripe = Session.retrieve(sessionId);

        String paymentIntentId = sessionStripe.getPaymentIntent();

        // 3. Actualizar el pago
        pago.setEstado(true);
        pago.setAutorizacion(paymentIntentId);
        pago.setFactura(factura);
        pagoService.save(pago);

        // 4. Limpiar sesión
        session.removeAttribute("carritoCompra");
        session.removeAttribute("pagoId");

        return "success";
    }
}

