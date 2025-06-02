package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.PerfilDatos;
import com.grupo3.pawHome.repositories.PerfilDatosRespository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilDatosService {
    private final PerfilDatosRespository perfilDatosRespository;

    public PerfilDatosService(PerfilDatosRespository perfilDatosRespository) {
        this.perfilDatosRespository = perfilDatosRespository;
    }

    public Optional<PerfilDatos> findById(int id) {
        return perfilDatosRespository.findById(id);
    }
}
