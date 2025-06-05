package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Animal;
import com.grupo3.pawHome.repositories.AnimalRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> findAll() { return animalRepository.findAll(); }

    public Optional<Animal> findById(int id) {return animalRepository.findById(id);
    }

    public Page<Animal> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Animal> animales = animalRepository.findAllByAnimalServicio(false);
        List<Animal> list;

        if (animales.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, animales.size());
            list = animales.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), animales.size());
    }
}
