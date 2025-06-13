package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.grupo3.pawHome.dtos.SubscriptionRequest;
import com.grupo3.pawHome.entities.*;
import com.grupo3.pawHome.services.*;
import com.grupo3.pawHome.util.SecurityUtil;
import com.grupo3.pawHome.util.SessionUtils;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private final TipoPagoService tipoPagoService;
    private final SecurityUtil securityUtil;
    private final AnimalService animalService;

    public ProductCheckoutController(StripeService stripeService,
                                     PagoService pagoService,
                                     FacturaService facturaService,
                                     TallaService tallaService,
                                     UsuarioService usuarioService,
                                     MetodoPagoService metodoPagoService,
                                     TipoPagoService tipoPagoService,
                                     SecurityUtil securityUtil,
                                     AnimalService animalService) {
        this.stripeService = stripeService;
        this.pagoService = pagoService;
        this.facturaService = facturaService;
        this.tallaService = tallaService;
        this.usuarioService = usuarioService;
        this.metodoPagoService = metodoPagoService;
        this.tipoPagoService = tipoPagoService;
        this.securityUtil = securityUtil;
        this.animalService = animalService;
    }

    @PostMapping("/product/v1/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(
            @RequestBody List<ProductRequest> productRequests,
            @AuthenticationPrincipal Usuario usuarioAutenticado
    ) {
        try {
            Optional<Usuario> userOptional = usuarioService.findById(usuarioAutenticado.getId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        StripeResponse.builder()
                                .status("FAILED")
                                .message("Usuario no autenticado. Por favor inicia sesión")
                                .build()
                );
            }

            Usuario userCustomerId = usuarioService.ensureStripeCustomerExists(userOptional.get());
            securityUtil.updateAuthenticatedUser(userCustomerId);

            StripeResponse stripeResponse = stripeService.checkoutProducts(productRequests, userCustomerId.getStripeCustomerId());

            return ResponseEntity.ok(stripeResponse);

        } catch (StripeException e) {
            return ResponseEntity.internalServerError().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Error en Stripe: " + e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping("/success")
    public RedirectView procesarPagoExitoso(@RequestParam("session_id") String sessionId,
                                            @AuthenticationPrincipal Usuario usuario,
                                            HttpSession session) throws StripeException {

        String descripcion = (session.getAttribute("motivo") == null)? "" : session.getAttribute("motivo").toString();

        Factura factura = new Factura();
        factura.setUsuario(usuario);
        factura.setFecha(LocalDate.now());
        factura.setDescripcion(descripcion);

        List<LineaFactura> lineas = new ArrayList<>();

        if(descripcion.equals("Compra en Tienda") || descripcion.equals("ServicioPeluqueria")){

            List<ItemCarritoDTO> carrito = SessionUtils.obtenerCarritoSeguro(session);

            if (carrito.isEmpty()) { return new RedirectView("/cancel"); }

            for (ItemCarritoDTO item : carrito) {
                Optional<Talla> talla = tallaService.findById(item.getTalla().getId());
                if (talla.isPresent()) {
                    Talla t = talla.get();
                    t.setStock(t.getStock() - item.getCantidad());
                    tallaService.save(t);
                }
            }

            double total = carrito.stream()
                    .mapToDouble(item -> item.getPrecioUnitario() * item.getCantidad())
                    .sum();
            factura.setPrecio(total);

            lineas = carrito.stream().map(item -> {
                LineaFactura linea = new LineaFactura();
                linea.setNombre(item.getProducto().getNombre());
                linea.setCantidad(item.getCantidad());
                linea.setDescripcion(item.getProducto().getDescripcion());
                linea.setTarifa(item.getTarifa());
                linea.setFactura(factura);
                return linea;
            }).toList();

            factura.setLineaFacturas(lineas);
            session.removeAttribute("carrito");
        }

        if(descripcion.equals("Donacion")){
            Double precio = (Double) session.getAttribute("precio");

            LineaFactura linea = new LineaFactura();
            linea.setNombre("Donacion");
            linea.setDescripcion("Donacion - " + precio  + "€");
            linea.setCantidad(1);
            linea.setFactura(factura);

            lineas.add(linea);

            factura.setPrecio(precio);
            factura.setLineaFacturas(lineas);
        }

        if(descripcion.equals("Servicio")){
            LineaFactura linea = new LineaFactura();
            ItemCarritoDTO item = (ItemCarritoDTO) session.getAttribute("itemServicio");
            double precioTotal = item.getCantidad()*item.getPrecioUnitario();
            linea.setNombre(item.getProducto().getNombre());
            linea.setCantidad(item.getCantidad());
            linea.setDescripcion(item.getProducto().getDescripcion());
            linea.setFactura(factura);

            lineas.add(linea);

            factura.setPrecio(precioTotal);
            factura.setLineaFacturas(lineas);
        }

        // --- Stripe: obtener PaymentMethod y su fingerprint ---
        Session sessionStripe = Session.retrieve(sessionId);
        String paymentIntentId = sessionStripe.getPaymentIntent();
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        String stripePaymentMethodId = paymentIntent.getPaymentMethod();

        // Recupera el PaymentMethod de Stripe para obtener la fingerprint
        com.stripe.model.PaymentMethod paymentMethod = com.stripe.model.PaymentMethod.retrieve(stripePaymentMethodId);
        String fingerprint = paymentMethod.getCard().getFingerprint();

        // Busca en tu base de datos por fingerprint para evitar duplicados
        MetodoPago metodoPago = metodoPagoService.findByFingerPrintAndUsuario(fingerprint, usuario);

        if (metodoPago == null) {
            // Si no existe, crea uno nuevo
            metodoPago = new MetodoPago();
            metodoPago.setStripePaymentMethodId(paymentMethod.getId());
            metodoPago.setMarcaTarjeta(paymentMethod.getCard().getBrand());
            metodoPago.setUltimosDigitos(paymentMethod.getCard().getLast4());
            metodoPago.setExpMes(paymentMethod.getCard().getExpMonth().intValue());
            metodoPago.setExpAnio(paymentMethod.getCard().getExpYear().intValue());
            metodoPago.setNombreTitular(paymentMethod.getBillingDetails().getName());
            metodoPago.setUsuario(usuario);
            metodoPago.setAlias("Tarjeta Stripe");
            metodoPago.setActivo(true);
            metodoPago.setFingerPrint(fingerprint); // Necesitas este campo en tu entidad

            // Opcional: asigna tipo de pago si lo tienes en tu modelo
            Optional<TipoPago> tipoPago = tipoPagoService.findByNombreContains("Tarjeta Credito");
            tipoPago.ifPresent(metodoPago::setTipoPago);

            metodoPagoService.save(metodoPago);
        } else {
            // Si existe, actualiza el ID si ha cambiado y márcalo como activo
            metodoPago.setStripePaymentMethodId(paymentMethod.getId());
            metodoPago.setActivo(true);
            metodoPagoService.save(metodoPago);
        }

        Pago pago = new Pago();
        pago.setEstado(true);
        pago.setAutorizacion(paymentIntentId);
        pago.setFactura(factura);
        pago.setUsuario(usuario);

        pago.setMetodoPago(metodoPago);

        facturaService.save(factura);
        pagoService.save(pago);


        session.removeAttribute("motivo");

        return new RedirectView("/pago-correcto");
    }

    @GetMapping("/cancel")
    public RedirectView procesarPagoIncorrecto(){

        return new RedirectView("/pago-incorrecto");
    }

    @PostMapping("/apadrinar/{id}/checkout-suscripcion")
    public ResponseEntity<StripeResponse> checkoutApadrinamiento(
            @PathVariable("id") Integer animalId,
            @RequestBody SubscriptionRequest request,
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            HttpSession session
    ) throws StripeException {
        Optional<Usuario> userOptional = usuarioService.findById(usuarioAutenticado.getId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Usuario no autenticado. Por favor inicia sesión")
                            .build()
            );
        }
        Usuario usuario = userOptional.get();
        usuarioService.ensureStripeCustomerExists(usuario);

        Animal animal = animalService.findById(animalId)
                .orElseThrow(() -> new IllegalArgumentException("Animal no encontrado"));

        if (animal.getStripeProductId() == null || animal.getStripeProductId().isEmpty()) {
            String productId = stripeService.createStripeProductForAnimal(animal);
            animal.setStripeProductId(productId);
            animalService.save(animal);
        }

        session.setAttribute("animal", animal);

        Price price = stripeService.getOrCreatePriceId(request.getAporteMensual(), animal);

        StripeResponse stripeResponse = stripeService.checkoutSubscription(price, usuario.getStripeCustomerId());

        return ResponseEntity.ok(stripeResponse);
    }
}

