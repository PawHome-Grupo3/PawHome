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

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuController {

    private final UsuarioRepository usuarioRepository;
    private final EditarUsuarioPerfilService editarUsuarioPerfilService;
    private final RolRepository rolRepository;

    public AdminUsuController(UsuarioRepository usuarioRepository, EditarUsuarioPerfilService editarUsuarioPerfilService, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.editarUsuarioPerfilService = editarUsuarioPerfilService;
        this.rolRepository = rolRepository;
    }

    // Listar usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        usuarios.sort(Comparator.comparing(Usuario::getId));
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

    // Editar usuario existente
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        EditarUsuarioPerfilDTO dto = editarUsuarioPerfilService.obtenerDtoDesdeUsuario(id);
        model.addAttribute("usuario", dto);
        model.addAttribute("roles", rolRepository.findAll());
        return "editarAdminUsuarios";
    }

    // Actualizar usuario
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
