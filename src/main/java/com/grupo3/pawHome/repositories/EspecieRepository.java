package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.entities.Especie;
import com.grupo3.pawHome.entities.Raza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Integer> { }