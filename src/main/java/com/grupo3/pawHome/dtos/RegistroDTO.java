package com.grupo3.pawHome.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDTO {

    @NotBlank(message = "El nickname no puede estar vacío")
    private String nickname;

    @Email(message = "El email no tiene un formato válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
}
