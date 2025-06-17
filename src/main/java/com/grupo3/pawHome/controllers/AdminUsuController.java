package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.dtos.EditarUsuarioPerfilDTO;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.RolRepository;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import com.grupo3.pawHome.services.EditarUsuarioPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EditarUsuarioPerfilService editarUsuarioPerfilService;
    @Autowired
    private RolRepository rolRepository;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("usuario", new Usuario()); // Formulario vacío para crear
        return "adminUsuarios";
    }

    // Guardar usuario (nuevo o editado)
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/admin/usuarios";
    }

    // Editar usuario (rellena el formulario con los datos del usuario)
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        EditarUsuarioPerfilDTO dto = editarUsuarioPerfilService.obtenerDtoDesdeUsuario(id);
        model.addAttribute("usuario", dto);
        model.addAttribute("roles", rolRepository.findAll());
        return "editarAdminUsuarios";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(
            @PathVariable Integer id,
            @ModelAttribute EditarUsuarioPerfilDTO dto) {

        editarUsuarioPerfilService.actualizarDesdeDto(id, dto);
        return "redirect:/admin/usuarios";
    }

    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        usuarioRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }
}
