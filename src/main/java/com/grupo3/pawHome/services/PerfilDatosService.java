package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.repositories.PerfilDatosRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilDatosService {
    private final PerfilDatosRepository perfilDatosRepository;

    public PerfilDatosService(PerfilDatosRepository perfilDatosRepository) {
        this.perfilDatosRepository = perfilDatosRepository;
    }

    public Optional<PerfilDatos> findById(int id) {
        return perfilDatosRepository.findById(id);
    }

    public PerfilDatos save(PerfilDatos perfilDatos) { return perfilDatosRepository.save(perfilDatos); }
}
