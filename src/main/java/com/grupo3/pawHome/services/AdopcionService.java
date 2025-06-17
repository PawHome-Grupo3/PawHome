package com.grupo3.pawHome.services;

import com.grupo3.pawHome.dtos.FormAdoptaDTO;
import com.grupo3.pawHome.entities.Adopcion;
import com.grupo3.pawHome.entities.Animal;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.AdopcionRepository;
import com.grupo3.pawHome.repositories.AnimalRepository;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdopcionService {

    private final UsuarioRepository usuarioRepository;
    private final AnimalRepository animalRepository;
    private final AdopcionRepository adopcionRepository;

    public AdopcionService(AdopcionRepository adopcionRepository,
                           UsuarioRepository usuarioRepository, AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
        this.adopcionRepository = adopcionRepository;
        this.usuarioRepository = usuarioRepository;
    }


    public Adopcion formAdoptaDtoToDocumentoAdopcion(FormAdoptaDTO formAdoptaDTO) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(formAdoptaDTO.getUsuarioId());
        Optional<Animal> animalOpt = animalRepository.findById(formAdoptaDTO.getAnimalId());

        Adopcion adopcion = new Adopcion();
        adopcion.setOcupacion(formAdoptaDTO.getOcupacion());
        adopcion.setOtrosAnimales(formAdoptaDTO.getOtrosAnimales());
        adopcion.setDeAcuerdo(formAdoptaDTO.getDeAcuerdo());
        adopcion.setHijos(formAdoptaDTO.getHijos());
        adopcion.setCaracteristicaMascota(formAdoptaDTO.getCaracteristicaMascota());
        adopcion.setExperienciaPrevia(formAdoptaDTO.getExperienciaPrevia());
        adopcion.setTiempoSolo(formAdoptaDTO.getTiempoSolo());
        adopcion.setDondeVivira(formAdoptaDTO.getDondeVivira());
        adopcion.setVeterinario(formAdoptaDTO.getVeterinario());
        adopcion.setVisitasSeguimiento(formAdoptaDTO.getVisitasSeguimiento());
        adopcion.setFirma(formAdoptaDTO.getFirma());
        adopcion.setFechaFormulario(formAdoptaDTO.getFechaFormulario());
        adopcion.setAnimal(animalOpt.orElse(null));
        adopcion.setUsuario(usuarioOpt.orElse(null));
        adopcion.setAceptado(null);

        return adopcion;
    }

    public Optional<Adopcion> findById(Integer id) { return adopcionRepository.findById(id); }

    public List<Adopcion> findaAllByUsuario(Usuario usuario) { return adopcionRepository.findAllByUsuario(usuario); }

    public List<Adopcion> findAll() {
        return adopcionRepository.findAll();
    }

    @Transactional
    public void cambiarEstadoAdopcion(Integer id, boolean aceptado) {
        adopcionRepository.findById(id).ifPresent(adopcion -> {
            adopcion.setAceptado(aceptado);
            adopcionRepository.save(adopcion);
        });
    }
}
