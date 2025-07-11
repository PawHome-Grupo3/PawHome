package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.grupo3.pawHome.entities.*;
import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import com.grupo3.pawHome.services.*;
import com.grupo3.pawHome.util.SecurityUtil;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class CarritoController {
    private final ProductoService productoService;
    private final TarifaService tarifaService;
    private final TallaService tallaService;
    private final StripeService stripeService;
    private final UsuarioService usuarioService;
    private final SecurityUtil securityUtil;

    public CarritoController(ProductoService productoService,
                             TarifaService tarifaService,
                             TallaService tallaService,
                             StripeService stripeService, UsuarioService usuarioService, SecurityUtil securityUtil) {
        this.productoService = productoService;
        this.tarifaService = tarifaService;
        this.tallaService = tallaService;
        this.stripeService = stripeService;
        this.usuarioService = usuarioService;
        this.securityUtil = securityUtil;
    }

    @GetMapping("/tienda/carrito")
    public String verCarrito(HttpSession session, Model model) {
        List<ItemCarritoDTO> carrito = getCarrito(session);
        model.addAttribute("carrito", carrito);

        double total = carrito.stream()
                .mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario())
                .sum();

        model.addAttribute("total", new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).toPlainString());
        return "carrito";
    }

    @PostMapping("/tienda/carrito/agregar")
    public String agregarAlCarrito(@RequestParam("productoId") int productoId,
                                   @RequestParam("tallaId") int tallaId,
                                   @RequestParam("cantidad") int cantidad,
                                   HttpSession session) {

        Optional<Producto> productoOpt = productoService.findById(productoId);
        Optional<Talla> tallaOpt = tallaService.findById(tallaId);
        Optional<Tarifa> tarifaOpt = tarifaService.findTopByProductoIdOrderByFechaDesdeDesc(productoId);

        if (productoOpt.isPresent() && tallaOpt.isPresent() && tarifaOpt.isPresent()) {
            Producto producto = productoOpt.get();
            Talla talla = tallaOpt.get();
            Tarifa tarifa = tarifaOpt.get();

            double precio = tarifa.getPrecioUnitario();

            List<ItemCarritoDTO> carrito = getCarrito(session);

            Optional<ItemCarritoDTO> existente = carrito.stream()
                    .filter(item -> item.getProducto().getId() == productoId &&
                            item.getTalla().getId() == tallaId)
                    .findFirst();

            if (existente.isPresent()) {
                existente.get().setCantidad(existente.get().getCantidad() + cantidad);
            } else {
                carrito.add(new ItemCarritoDTO(producto, talla, cantidad, precio, tarifa));
            }

            session.setAttribute("carrito", carrito);
        }

        return "redirect:/tienda/carrito";
    }

    @PostMapping("/tienda/carrito/eliminar")
    public String eliminarDelCarrito(@RequestParam("productoId") int productoId,
                                     @RequestParam("tallaId") int tallaId,
                                     HttpSession session) {

        List<ItemCarritoDTO> carrito = getCarrito(session);
        carrito.removeIf(item -> item.getProducto().getId() == productoId &&
                item.getTalla().getId() == tallaId);

        session.setAttribute("carrito", carrito);
        return "redirect:/tienda/carrito";
    }

    @PostMapping("/tienda/carrito/actualizar")
    public String actualizarCantidad(@RequestParam("productoId") int productoId,
                                     @RequestParam("tallaId") int tallaId,
                                     @RequestParam("cantidad") int cantidad,
                                     HttpSession session) {

        List<ItemCarritoDTO> carrito = getCarrito(session);

        if (cantidad <= 0) {
            carrito.removeIf(item -> item.getProducto().getId() == productoId && item.getTalla().getId() == tallaId);
        } else {
            for (ItemCarritoDTO item : carrito) {
                if (item.getProducto().getId() == productoId && item.getTalla().getId() == tallaId) {
                    if (cantidad <= item.getTalla().getStock()) {
                        item.setCantidad(cantidad);
                    } else {
                        item.setCantidad(item.getTalla().getStock());
                    }
                    break;
                }
            }
        }

        session.setAttribute("carrito", carrito);
        return "redirect:/tienda/carrito";
    }

    @PostMapping("/tienda/carrito/vaciar")
    public String vaciarCarrito(HttpSession session) {

        session.removeAttribute("carrito");
        return "redirect:/tienda/carrito";
    }

    @SuppressWarnings("unchecked")
    private List<ItemCarritoDTO> getCarrito(HttpSession session) {
        Object carrito = session.getAttribute("carrito");

        if (carrito == null) {
            List<ItemCarritoDTO> nuevoCarrito = new ArrayList<>();
            session.setAttribute("carrito", nuevoCarrito);
            return nuevoCarrito;
        }

        return (List<ItemCarritoDTO>) carrito;
    }

    @PostMapping("/tienda/carrito/checkout")
    public ResponseEntity<StripeResponse> checkoutDesdeCarrito(
            @AuthenticationPrincipal MyUserDetails userDetails,
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

        List<ItemCarritoDTO> carrito = getCarrito(session);

        if (carrito.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("El carrito está vacío.")
                            .build()
            );
        }

        session.setAttribute("motivo", "Compra en Tienda");

        List<ProductRequest> productRequests = stripeService.convertirCarritoAProductRequests(carrito);
        Optional<Usuario> user = usuarioService.findById(usuario.getId());

        if (user.isPresent()) {
            Usuario userCustomerId = usuarioService.ensureStripeCustomerExists(user.get());
            StripeResponse stripeResponse = stripeService.checkoutProducts(productRequests, userCustomerId.getStripeCustomerId());
            securityUtil.updateAuthenticatedUser(userCustomerId);
            return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
        }
        else{
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("Usuario no autenticado. Por favor inicia sesión")
                            .build()
            );
        }
    }
}

