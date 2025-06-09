package com.grupo3.pawHome.services;

import com.grupo3.pawHome.config.StripeConfig;
import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StripeService {

    private final StripeConfig stripeConfig;

    public StripeService(StripeConfig stripeConfig) {
        this.stripeConfig = stripeConfig;
    }

    public StripeResponse checkoutProducts(List<ProductRequest> productRequests, String customerId) {
        Stripe.apiKey = stripeConfig.getSecretKey();

        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:8080/cancel");

        paramsBuilder.setCustomer(customerId);

        for (ProductRequest productRequest : productRequests) {
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(productRequest.getName())
                            .build();

            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "USD")
                            .setUnitAmount(productRequest.getAmount())
                            .setProductData(productData)
                            .build();

            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(productRequest.getQuantity())
                            .setPriceData(priceData)
                            .build();

            paramsBuilder.addLineItem(lineItem);
        }

        try {
            Session session = Session.create(paramsBuilder.build());

            return StripeResponse.builder()
                    .status("SUCCESS")
                    .message("Payment session created")
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .build();
        } catch (StripeException e) {
            e.printStackTrace();
            return StripeResponse.builder()
                    .status("FAILED")
                    .message("Stripe error: " + e.getMessage())
                    .build();
        }
    }

    public List<ProductRequest> convertirCarritoAProductRequests(List<ItemCarritoDTO> carrito) {
        return carrito.stream()
                .map(item -> new ProductRequest(
                        (long) (item.getPrecioUnitario() * 100),
                        (long) item.getCantidad(),
                        item.getProducto().getNombre(),
                        "eur"
                ))
                .toList();
    }
}
