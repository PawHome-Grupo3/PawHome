package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.grupo3.pawHome.entities.Producto;
import com.grupo3.pawHome.entities.Talla;
import com.grupo3.pawHome.entities.Tarifa;
import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import com.grupo3.pawHome.services.ProductoService;
import com.grupo3.pawHome.services.StripeService;
import com.grupo3.pawHome.services.TallaService;
import com.grupo3.pawHome.services.TarifaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public CarritoController(ProductoService productoService, TarifaService tarifaService, TallaService tallaService, StripeService stripeService) {
        this.productoService = productoService;
        this.tarifaService = tarifaService;
        this.tallaService = tallaService;
        this.stripeService = stripeService;
    }

    @GetMapping("/tienda/carrito")
    public String verCarrito(HttpSession session, Model model) {
        List<ItemCarritoDTO> carrito = getCarrito(session);
        model.addAttribute("carrito", carrito);

        double total = carrito.stream()
                .mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario())
                .sum();

        model.addAttribute("total", new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).toPlainString());
        return "carrito"; // Thymeleaf template
    }

    @PostMapping("/tienda/carrito/agregar")
    public String agregarAlCarrito(@RequestParam("productoId") int productoId,
                                   @RequestParam("tallaId") int tallaId,
                                   @RequestParam("cantidad") int cantidad,
                                   HttpSession session) {
        Optional<Producto> productoOpt = productoService.findById(productoId);
        Optional<Talla> tallaOpt = tallaService.findById(tallaId);
        double precio = tarifaService.findTopByProductoIdOrderByFechaDesdeDesc(productoId)
                .map(Tarifa::getPrecioUnitario).orElse(0.0);

        if (productoOpt.isPresent() && tallaOpt.isPresent()) {
            Producto producto = productoOpt.get();
            Talla talla = tallaOpt.get();

            List<ItemCarritoDTO> carrito = getCarrito(session);

            // Verifica si ya existe ese producto+talla
            Optional<ItemCarritoDTO> existente = carrito.stream()
                    .filter(item -> item.getProducto().getId() == productoId &&
                            item.getTalla().getId() == tallaId)
                    .findFirst();

            if (existente.isPresent()) {
                existente.get().setCantidad(existente.get().getCantidad() + cantidad);
            } else {
                carrito.add(new ItemCarritoDTO(producto, talla, cantidad, precio));
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

        // Si cantidad es 0, se elimina del carrito
        if (cantidad <= 0) {
            carrito.removeIf(item -> item.getProducto().getId() == productoId && item.getTalla().getId() == tallaId);
        } else {
            for (ItemCarritoDTO item : carrito) {
                if (item.getProducto().getId() == productoId && item.getTalla().getId() == tallaId) {
                    // Verifica stock si es necesario
                    if (cantidad <= item.getTalla().getStock()) {
                        item.setCantidad(cantidad);
                    } else {
                        // Opcional: puedes enviar un error o limitar al stock máximo
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
    public ResponseEntity<StripeResponse> checkoutDesdeCarrito(HttpSession session) {
        List<ItemCarritoDTO> carrito = getCarrito(session);

        if (carrito.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    StripeResponse.builder()
                            .status("FAILED")
                            .message("El carrito está vacío.")
                            .build()
            );
        }

        List<ProductRequest> productRequests = stripeService.convertirCarritoAProductRequests(carrito);
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequests);

        return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
    }
}

