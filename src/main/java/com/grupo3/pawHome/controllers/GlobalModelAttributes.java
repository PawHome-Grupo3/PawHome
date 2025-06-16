package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.dtos.CategoriaDto;
import com.grupo3.pawHome.entities.Categoria;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.CategoriaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    private final CategoriaService categoriaService;

    public GlobalModelAttributes(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @ModelAttribute
    public void agregarCategoriasSoloParaTienda(HttpServletRequest request, Model model) {
        String uri = request.getRequestURI();
        if (uri.startsWith("/tienda")) {
            List<Categoria> categoriasNav = categoriaService.findByNombreContainingIgnoreCase("tienda");
            List<CategoriaDto> categoriasNavDto = categoriasNav.stream()
                    .map(categoria -> new CategoriaDto(categoria, true))
                    .toList();

            model.addAttribute("categoriasNav", categoriasNav);
            model.addAttribute("categoriasNavDto", categoriasNavDto);
        }
    }

    @ModelAttribute
    public void agregarUsuarioSoloParaPerfil(HttpServletRequest request,
                                             @AuthenticationPrincipal MyUserDetails userDetails,
                                             Model model) {

        String uri = request.getRequestURI();

        if (uri.startsWith("/perfil")) {
            Usuario usuario = userDetails.getUsuario();
            model.addAttribute("usuario", usuario);
        }
    }
}
