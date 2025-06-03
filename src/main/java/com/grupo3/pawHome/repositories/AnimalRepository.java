package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    List<Animal> findAllByAnimalServicio(boolean animalServicio);
}