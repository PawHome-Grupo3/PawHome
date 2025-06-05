package com.grupo3.pawHome.dtos;

import jakarta.persistence.SecondaryTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDTO {
    private String code;
    private String name;

    public CountryDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
