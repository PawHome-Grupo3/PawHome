package com.grupo3.pawHome.services;

import com.grupo3.pawHome.entities.Animales;
import com.grupo3.pawHome.repositories.AnimalesRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalesService {
    private final AnimalesRepository animalesRepository;

    public AnimalesService(AnimalesRepository repository, AnimalesRepository animalesRepository) {
        this.animalesRepository = animalesRepository;
    }

    public List<Animales> findAll() { return animalesRepository.findAll(); }

    public Optional<Animales> findById(int id) {return animalesRepository.findById(id);
    }

    public Page<Animales> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Animales> animales = animalesRepository.findAllByAnimalServicio(false);
        List<Animales> list;

        if (animales.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, animales.size());
            list = animales.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), animales.size());
    }
}
