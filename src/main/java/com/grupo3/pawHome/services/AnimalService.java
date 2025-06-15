package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Animal;
import com.grupo3.pawHome.repositories.AnimalRepository;
import com.grupo3.pawHome.specifications.AnimalSpecifications;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> findAll() { return animalRepository.findAll(); }

    public Page<Animal> findAll(Pageable pageable) { return animalRepository.findAll(pageable); }

    public Optional<Animal> findById(int id) { return animalRepository.findById(id); }

    public Animal save(Animal animal) { return animalRepository.save(animal); }

    public Page<Animal> buscarAnimales(
            String keyword, String adoptado, Integer especieId, Integer razaId, Pageable pageable) {

        Specification<Animal> spec = AnimalSpecifications.animalServicioEsFalse();

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and(AnimalSpecifications.nombreContiene(keyword));
        }

        if (adoptado != null && !adoptado.isEmpty()) {
            spec = switch (adoptado) {
                case "apadrinar" -> spec.and(AnimalSpecifications.adoptadoEs(false));
                case "adoptar" -> spec.and(AnimalSpecifications.adoptadoEs(false))
                        .and(AnimalSpecifications.caracterSocialEs(true));
                case "adoptado" -> spec.and(AnimalSpecifications.adoptadoEs(true));
                default -> spec;
            };
        }

        if (especieId != null) {
            spec = spec.and(AnimalSpecifications.especieIdEs(especieId));
        }
        if (razaId != null) {
            spec = spec.and(AnimalSpecifications.razaIdEs(razaId));
        }

        return animalRepository.findAll(spec, pageable);
    }
}
