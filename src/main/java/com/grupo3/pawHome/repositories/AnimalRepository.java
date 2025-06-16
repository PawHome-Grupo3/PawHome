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

    Page<Animal> findByNombreContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Animal> findAllByAnimalServicioIsFalse(Pageable pageable);

    Page<Animal> findByNombreContainingIgnoreCaseAndAdoptado(String keyword, boolean isAdoptado, Pageable pageable);

    Page<Animal> findByAdoptado(boolean isAdoptado, Pageable pageable);

    // 1. keyword + adoptado + especie + raza
    Page<Animal> findByNombreContainingIgnoreCaseAndAdoptadoAndEspecie_IdAndEspecie_Raza_Id(
            String nombre, boolean adoptado, Integer especieId, Integer razaId, Pageable pageable
    );

    // 2. keyword + adoptado + especie
    Page<Animal> findByNombreContainingIgnoreCaseAndAdoptadoAndEspecie_Id(
            String nombre, boolean adoptado, Integer especieId, Pageable pageable
    );

    // 3. keyword + adoptado + raza
    Page<Animal> findByNombreContainingIgnoreCaseAndAdoptadoAndEspecie_Raza_Id(
            String nombre, boolean adoptado, Integer razaId, Pageable pageable
    );

    // 4. keyword + especie + raza
    Page<Animal> findByNombreContainingIgnoreCaseAndEspecie_IdAndEspecie_Raza_Id(
            String nombre, Integer especieId, Integer razaId, Pageable pageable
    );

    // 5. keyword + especie
    Page<Animal> findByNombreContainingIgnoreCaseAndEspecie_Id(
            String nombre, Integer especieId, Pageable pageable
    );

    // 6. keyword + raza
    Page<Animal> findByNombreContainingIgnoreCaseAndEspecie_Raza_Id(
            String nombre, Integer razaId, Pageable pageable
    );

    // 9. adoptado + especie + raza
    Page<Animal> findByAdoptadoAndEspecie_IdAndEspecie_Raza_Id(
            boolean adoptado, Integer especieId, Integer razaId, Pageable pageable
    );

    // 10. adoptado + especie
    Page<Animal> findByAdoptadoAndEspecie_Id(
            boolean adoptado, Integer especieId, Pageable pageable
    );

    // 11. adoptado + raza
    Page<Animal> findByAdoptadoAndEspecie_Raza_Id(
            boolean adoptado, Integer razaId, Pageable pageable
    );

    // 13. especie + raza
    Page<Animal> findByEspecie_IdAndEspecie_Raza_Id(
            Integer especieId, Integer razaId, Pageable pageable
    );

    // 14. especie solo
    Page<Animal> findByEspecie_Id(
            Integer especieId, Pageable pageable
    );

    // 15. raza solo
    Page<Animal> findByEspecie_Raza_Id(
            Integer razaId, Pageable pageable
    );

    // 16. Paseable solo
    List<Animal> findAllByPaseable(boolean paseable);
}