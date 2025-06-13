package com.grupo3.pawHome.util;

import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import com.grupo3.pawHome.entities.Animal;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("sessionUtils")
public class SessionUtils {
    public static List<ItemCarritoDTO> obtenerCarritoSeguro(HttpSession session) {
        Object obj = session.getAttribute("carrito");

        if (obj instanceof List<?>) {
            List<?> lista = (List<?>) obj;
            List<ItemCarritoDTO> carrito = new ArrayList<>();

            for (Object item : lista) {
                if (item instanceof ItemCarritoDTO) {
                    carrito.add((ItemCarritoDTO) item);
                } else {
                    return new ArrayList<>();
                }
            }

            return carrito;
        }

        return new ArrayList<>();
    }

    public static Animal obtenerAnimalSeguro(HttpSession session) {
        Object obj = session.getAttribute("animal");

        if (obj instanceof Animal) { return (Animal) obj; }
        else{ return new Animal(); }
    }
}
