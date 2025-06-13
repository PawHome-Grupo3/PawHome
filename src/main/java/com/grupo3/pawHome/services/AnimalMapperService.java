package com.grupo3.pawHome.services;

import com.grupo3.pawHome.dtos.AnimalDto;
import com.grupo3.pawHome.entities.Animal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class AnimalMapperService {

    public AnimalDto toDto(Animal animal) {
        if (animal == null) {
            return null;
        }

        AnimalDto dto = new AnimalDto();
        dto.setId(animal.getId());
        dto.setNombre(animal.getNombre());
        dto.setChip(animal.getChip());
        dto.setPeso(animal.getPeso());
        dto.setFechaNacimiento(animal.getFechaNacimiento());
        dto.setCaracterSocial(animal.isCaracterSocial());
        dto.setDescripcion(animal.getDescripcion());
        dto.setGenero(animal.isGenero());
        dto.setOrigen(animal.isOrigen());
        dto.setAdoptado(animal.isAdoptado());
        dto.setFechaLlegada(animal.getFechaLlegada());
        dto.setPaseable(animal.isPaseable());
        dto.setAnimalServicio(animal.isAnimalServicio());
        dto.setRutaImg1(animal.getRutaImg1());
        dto.setRutaImg2(animal.getRutaImg2());
        dto.setRutaImg3(animal.getRutaImg3());
        dto.setStripeProductId(animal.getStripeProductId());
        dto.setEdad(calcularEdad(animal.getFechaNacimiento()));

        return dto;
    }

    private String calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) return "Desconocida";
        Period periodo = Period.between(fechaNacimiento, LocalDate.now());
        int anios = periodo.getYears();
        int meses = periodo.getMonths();
        int dias = periodo.getDays();

        if (anios == 0 && meses == 0) {
            return dias + " días";
        } else if (anios == 0) {
            return meses + " meses";
        } else if (meses == 0) {
            return anios + " años";
        } else {
            return anios + " años y " + meses + " meses";
        }
    }
}
