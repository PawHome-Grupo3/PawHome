package com.grupo3.pawHome.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class SetupIntentResponse {
    private String clientSecret;
}