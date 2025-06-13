package com.grupo3.pawHome.dtos;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuardarMetodoPagoRequest {
    private String paymentMethodId;
    private String alias;
}
