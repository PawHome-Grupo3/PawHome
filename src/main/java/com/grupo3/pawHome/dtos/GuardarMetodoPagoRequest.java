package com.grupo3.pawHome.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GuardarMetodoPagoRequest {
    private String paymentMethodId;
    private String alias;
}
