package com.grupo3.pawHome.util;

import com.grupo3.pawHome.dtos.ItemCarritoDTO;
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
}
