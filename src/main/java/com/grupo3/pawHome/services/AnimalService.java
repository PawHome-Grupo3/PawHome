package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Animal;
import com.grupo3.pawHome.repositories.AnimalRepository;
import org.springframework.data.domain.*;
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

    public Page<Animal> findByTitleContainingIgnoreCase(String keyword, Pageable pageable) { return animalRepository.findByNombreContainingIgnoreCase(keyword, pageable);}

    public Page<Animal> findAllByAnimalServicioIsFalse(Pageable pageable) {
        return animalRepository.findAllByAnimalServicioIsFalse(pageable);
    }
}
