package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.grupo3.pawHome.entities.*;
import com.grupo3.pawHome.services.*;
import com.grupo3.pawHome.util.SessionUtils;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
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
    private final UsuarioService usuarioService;
    private final MetodoPagoService metodoPagoService;

    public ProductCheckoutController(StripeService stripeService,
                                     PagoService pagoService,
                                     FacturaService facturaService,
                                     TallaService tallaService,
                                     UsuarioService usuarioService, MetodoPagoService metodoPagoService) {
        this.stripeService = stripeService;
        this.pagoService = pagoService;
        this.facturaService = facturaService;
        this.tallaService = tallaService;
        this.usuarioService = usuarioService;
        this.metodoPagoService = metodoPagoService;
    }

    @PostMapping("/product/v1/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody List<ProductRequest> productRequests,
                                                           @AuthenticationPrincipal Usuario usuario) {
        Optional<Usuario> user = usuarioService.findById(usuario.getId());
        if (user.isPresent()) {
            StripeResponse stripeResponse = stripeService.checkoutProducts(productRequests, user.get().getStripeCustomerId());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(stripeResponse);
        }

        else {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Usuario no autenticado. Por favor inicia sesión")
                            .build()
            );
        }
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

        Session sessionStripe = Session.retrieve(sessionId);
        String paymentIntentId = sessionStripe.getPaymentIntent();
        System.out.println(paymentIntentId);
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        System.out.println(paymentIntent);
        String stripePaymentMethodId = paymentIntent.getPaymentMethod();
        System.out.println(stripePaymentMethodId);

        MetodoPago metodoPago = metodoPagoService.findByStripePaymentMethodId(stripePaymentMethodId);

        Pago pago = new Pago();
        pago.setEstado(true);
        pago.setAutorizacion(paymentIntentId);
        pago.setFactura(factura);
        pago.setUsuario(usuario);
        if(metodoPago != null) pago.setMetodoPago(metodoPago);

        pagoService.save(pago);

        session.removeAttribute("carrito");

        return new RedirectView("/pago-correcto");
    }

    @GetMapping("/cancel")
    public RedirectView procesarPagoIncorrecto(){

        return new RedirectView("/pago-incorrecto");
    }
}

