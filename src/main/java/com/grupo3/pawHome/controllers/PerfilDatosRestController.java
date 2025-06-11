package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.repositories.PerfilDatosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/perfil-datos")
public class PerfilDatosRestController {

    @Autowired
    private PerfilDatosRepository perfilDatosRepository;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<PerfilDatos> obtenerPerfilDatos(@PathVariable Integer usuarioId) {
        Optional<PerfilDatos> optionalPerfilDatos = perfilDatosRepository.findById(usuarioId);

        if (optionalPerfilDatos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PerfilDatos perfil = optionalPerfilDatos.get().getUsuario().getPerfilDatos();

        if (perfil == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(perfil);
    }
}
