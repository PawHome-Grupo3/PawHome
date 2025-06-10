package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Apadrinar;
import com.grupo3.pawHome.repositories.ApadrinarRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ApadrinarService {
    private final ApadrinarRepository apadrinarRepository;

    public ApadrinarService(ApadrinarRepository repository) {
        this.apadrinarRepository = repository;
    }

    public List<Apadrinar> findAll() {
        return apadrinarRepository.findAll();
    }

    public Optional<Apadrinar> findById(int id) { return apadrinarRepository.findById(id); }

    public Set<Apadrinar> apadrinamientosActivosPorUsuario(int usuarioId) {
        return  apadrinarRepository.findByUsuarioIdAndFechaBajaIsNull(usuarioId);
    }

    public Set<Apadrinar> apadrinamientosInactivosPorUsuario(int usuarioId) {
        return  apadrinarRepository.findByUsuarioIdAndFechaBajaNotNull(usuarioId);
    }

    public Apadrinar save(Apadrinar apadrinamiento) { return apadrinarRepository.save(apadrinamiento); }

}
