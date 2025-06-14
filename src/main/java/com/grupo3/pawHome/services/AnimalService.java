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

//    public Page<Animal> buscarAnimalesConFiltros(
//            String keyword,
//            String adoptado,
//            Integer razaId,
//            Integer especieId,
//            Pageable pageable) {
//
//        boolean hasKeyword = keyword != null && !keyword.isEmpty();
//        boolean hasAdoptado = adoptado != null && !adoptado.isEmpty();
//        boolean hasEspecie = especieId != null;
//        boolean hasRaza = razaId != null;
//
//        if (hasKeyword) {
//            if (hasAdoptado) {
//                boolean isAdoptado = Boolean.parseBoolean(adoptado);
//
//                if (hasEspecie && hasRaza) {
//                    return animalRepository.findByNombreContainingIgnoreCaseAndAdoptadoAndEspecie_IdAndEspecie_Raza_Id(
//                            keyword, isAdoptado, especieId, razaId, pageable);
//                } else if (hasEspecie) {
//                    return animalRepository.findByNombreContainingIgnoreCaseAndAdoptadoAndEspecie_Id(
//                            keyword, isAdoptado, especieId, pageable);
//                } else if (hasRaza) {
//                    return animalRepository.findByNombreContainingIgnoreCaseAndAdoptadoAndEspecie_Raza_Id(
//                            keyword, isAdoptado, razaId, pageable);
//                } else {
//                    return animalRepository.findByNombreContainingIgnoreCaseAndAdoptado(
//                            keyword, isAdoptado, pageable);
//                }
//
//            } else {
//                if (hasEspecie && hasRaza) {
//                    return animalRepository.findByNombreContainingIgnoreCaseAndEspecie_IdAndEspecie_Raza_Id(
//                            keyword, especieId, razaId, pageable);
//                } else if (hasEspecie) {
//                    return animalRepository.findByNombreContainingIgnoreCaseAndEspecie_Id(
//                            keyword, especieId, pageable);
//                } else if (hasRaza) {
//                    return animalRepository.findByNombreContainingIgnoreCaseAndEspecie_Raza_Id(
//                            keyword, razaId, pageable);
//                } else {
//                    return animalRepository.findByNombreContainingIgnoreCase(keyword, pageable);
//                }
//
//            }
//        } else {
//            if (hasAdoptado) {
//                boolean isAdoptado = Boolean.parseBoolean(adoptado);
//
//                if (hasEspecie && hasRaza) {
//                    return animalRepository.findByAdoptadoAndEspecie_IdAndEspecie_Raza_Id(
//                            isAdoptado, especieId, razaId, pageable);
//                } else if (hasEspecie) {
//                    return animalRepository.findByAdoptadoAndEspecie_Id(
//                            isAdoptado, especieId, pageable);
//                } else if (hasRaza) {
//                    return animalRepository.findByAdoptadoAndEspecie_Raza_Id(
//                            isAdoptado, razaId, pageable);
//                } else {
//                    return animalRepository.findByAdoptado(isAdoptado, pageable);
//                }
//
//            } else {
//                if (hasEspecie && hasRaza) {
//                    return animalRepository.findByEspecie_IdAndEspecie_Raza_Id(
//                            especieId, razaId, pageable);
//                } else if (hasEspecie) {
//                    return animalRepository.findByEspecie_Id(especieId, pageable);
//                } else if (hasRaza) {
//                    return animalRepository.findByEspecie_Raza_Id(razaId, pageable);
//                } else {
//                    return animalRepository.findAllByAnimalServicioIsFalse(pageable);
//                }
//            }
//        }
//    }

    public Page<Animal> buscarAnimales(
            String keyword, String adoptado, Integer especieId, Integer razaId, Pageable pageable
    ) {
        Specification<Animal> spec = AnimalSpecifications.animalServicioEsFalse(); // Siempre filtra por animalServicio == false

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and(AnimalSpecifications.nombreContiene(keyword));
        }
        if (adoptado != null && !adoptado.isEmpty()) {
            boolean isAdoptado = Boolean.parseBoolean(adoptado);
            spec = spec.and(AnimalSpecifications.adoptadoEs(isAdoptado));
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
