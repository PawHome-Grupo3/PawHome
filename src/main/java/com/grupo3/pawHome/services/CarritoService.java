package com.grupo3.pawHome.services;

import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoService {

    public List<ItemCarritoDTO> getCarrito(HttpSession session) {
        Object carrito = session.getAttribute("carrito");
        if (carrito == null) {
            List<ItemCarritoDTO> nuevoCarrito = new ArrayList<>();
            session.setAttribute("carrito", nuevoCarrito);
            return nuevoCarrito;
        }
        return (List<ItemCarritoDTO>) carrito;
    }
}
