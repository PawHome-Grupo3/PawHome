package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer>, JpaSpecificationExecutor<Animal> {

    List<Animal> findAllByPaseable(boolean paseable);

    Page<Animal> findByRaza_Id(Integer razaId, Pageable pageable);

    Page<Animal> findByRaza_Especie_Id(Integer especieId, Pageable pageable);

    Page<Animal> findByRaza_Especie_IdAndRaza_Id(Integer especieId, Integer razaId, Pageable pageable);
}