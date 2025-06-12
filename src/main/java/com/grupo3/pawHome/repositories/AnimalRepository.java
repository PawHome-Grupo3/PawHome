package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer>{

    Page<Animal> findByNombreContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Animal> findAllByAnimalServicioIsFalse(Pageable pageable);
}