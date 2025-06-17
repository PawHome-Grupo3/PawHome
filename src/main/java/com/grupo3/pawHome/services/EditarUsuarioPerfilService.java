package com.grupo3.pawHome.services;

import com.grupo3.pawHome.dtos.EditarUsuarioPerfilDTO;
import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.entities.Rol;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.RolRepository;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditarUsuarioPerfilService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    public void actualizarDesdeDto(Integer id, EditarUsuarioPerfilDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNickname(dto.getNickname());
        usuario.setEmail(dto.getEmail());

        PerfilDatos perfil = usuario.getPerfilDatos();
        if (perfil == null) {
            perfil = new PerfilDatos();
        }

        perfil.setNombre(dto.getNombre());
        perfil.setApellidos(dto.getApellidos());
        perfil.setEdad(dto.getEdad());
        perfil.setDni(dto.getDni());
        perfil.setDireccion(dto.getDireccion());
        perfil.setPais(dto.getPais());
        perfil.setCiudad(dto.getCiudad());
        perfil.setCp(dto.getCp());
        perfil.setTelefono1(dto.getTelefono1());
        perfil.setTelefono2(dto.getTelefono2());
        perfil.setTelefono3(dto.getTelefono3());

        perfil.setUsuario(usuario);
        usuario.setPerfilDatos(perfil);

        if (dto.getRolId() != null) {
            Rol rol = rolRepository.findById(dto.getRolId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuario.setRol(rol);
        }

        usuarioRepository.save(usuario);
    }

    public EditarUsuarioPerfilDTO obtenerDtoDesdeUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        EditarUsuarioPerfilDTO dto = new EditarUsuarioPerfilDTO();
        dto.setId(usuario.getId());
        dto.setNickname(usuario.getNickname());
        dto.setEmail(usuario.getEmail());
        dto.setRolId(usuario.getRol() != null ? usuario.getRol().getId() : null);


        PerfilDatos perfil = usuario.getPerfilDatos();
        if (perfil != null) {
            dto.setNombre(perfil.getNombre());
            dto.setApellidos(perfil.getApellidos());
            dto.setEdad(perfil.getEdad());
            dto.setDni(perfil.getDni());
            dto.setDireccion(perfil.getDireccion());
            dto.setPais(perfil.getPais());
            dto.setCiudad(perfil.getCiudad());
            dto.setCp(perfil.getCp());
            dto.setTelefono1(perfil.getTelefono1());
            dto.setTelefono2(perfil.getTelefono2());
            dto.setTelefono3(perfil.getTelefono3());
        }


        return dto;
    }
}

