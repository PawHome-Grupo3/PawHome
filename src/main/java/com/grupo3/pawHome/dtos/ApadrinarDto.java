package com.grupo3.pawHome.dtos;

import com.grupo3.pawHome.entities.Animales;
import com.grupo3.pawHome.entities.Usuario;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.grupo3.pawHome.entities.Apadrinar}
 */
@Value
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ApadrinarDto implements Serializable {
    long id;
    double aporteMensual;
    LocalDate fechaInicio;
    LocalDate fechaRenovacion;
    LocalDate fechaBaja;
    Animales animales;
    Usuario usuario;
}