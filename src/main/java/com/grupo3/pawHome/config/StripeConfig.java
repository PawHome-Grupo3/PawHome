package com.grupo3.pawHome.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "stripe")
public class StripeConfig {
    private String secretKey;
    private String publicKey;
}
