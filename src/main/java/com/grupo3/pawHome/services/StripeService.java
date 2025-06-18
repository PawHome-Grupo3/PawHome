package com.grupo3.pawHome.services;

import com.grupo3.pawHome.config.StripeConfig;
import com.grupo3.pawHome.dtos.ItemCarritoDTO;
import com.grupo3.pawHome.dtos.ProductRequest;
import com.grupo3.pawHome.dtos.StripeResponse;
import com.grupo3.pawHome.entities.Animal;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StripeService {

    private final StripeConfig stripeConfig;
    private final AnimalService animalService;

    public StripeService(StripeConfig stripeConfig, AnimalService animalService) {
        this.stripeConfig = stripeConfig;
        this.animalService = animalService;
    }

    public StripeResponse checkoutProducts(List<ProductRequest> productRequests, String customerId) {
        Stripe.apiKey = stripeConfig.getSecretKey();

        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                //.setSuccessUrl("http://localhost:8080/success?session_id={CHECKOUT_SESSION_ID}")
                .setSuccessUrl("https://grupo03.serverjava.net/success?session_id={CHECKOUT_SESSION_ID}")
                //.setSuccessUrl("https://grupo03-desarrollo.serverjava.net/success?session_id={CHECKOUT_SESSION_ID}")
                //.setCancelUrl("https://grupo03-desarrollo.serverjava.net/cancel")
                .setCancelUrl("https://grupo03.serverjava.net/cancel")
                .setCustomer(customerId)
                .setPaymentIntentData(
                        SessionCreateParams.PaymentIntentData.builder()
                                .setSetupFutureUsage(SessionCreateParams.PaymentIntentData.SetupFutureUsage.OFF_SESSION)
                                .build()
                )
                .setAllowPromotionCodes(true)
                .setCustomerUpdate(SessionCreateParams.CustomerUpdate.builder()
                        .setAddress(SessionCreateParams.CustomerUpdate.Address.AUTO)
                        .build())
                .setPaymentMethodOptions(
                        SessionCreateParams.PaymentMethodOptions.builder()
                                .setCard(
                                        SessionCreateParams.PaymentMethodOptions.Card.builder()
                                                .setSetupFutureUsage(SessionCreateParams.PaymentMethodOptions.Card.SetupFutureUsage.OFF_SESSION)
                                                .build()
                                ).build()
                )
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD); // SOLO UNA VEZ

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

    public StripeResponse checkoutSubscription(Price price, String customerId) {
        Stripe.apiKey = stripeConfig.getSecretKey();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                //.setSuccessUrl("http://localhost:8080/apadrinar/success?session_id={CHECKOUT_SESSION_ID}")
                .setSuccessUrl("https://grupo03.serverjava.net/apadrinar/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("https://grupo03.serverjava.net/apadrinar/cancel")
                //.setCancelUrl("https://grupo03-desarrollo.serverjava.net/cancel")
                //.setCancelUrl("http://localhost:8080/apadrinar/cancel")
                .setCustomer(customerId)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPrice(price.getId())
                                .setQuantity(1L)
                                .build()
                )
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setSubscriptionData(
                        SessionCreateParams.SubscriptionData.builder()
                                .putMetadata("aporteMensual", String.valueOf(price.getUnitAmount()))
                                .build()
                )
                .build();

        try {
            Session session = Session.create(params);
            return StripeResponse.builder()
                    .status("SUCCESS")
                    .message("Subscription session created")
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

    public Price getOrCreatePriceId(double amount, Animal animal) throws StripeException {
        Stripe.apiKey = stripeConfig.getSecretKey();

        PriceCreateParams params = PriceCreateParams.builder()
                .setUnitAmount((long) (amount * 100))
                .setCurrency("eur")
                .setRecurring(PriceCreateParams.Recurring.builder()
                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                        .build())
                .setProduct(animal.getStripeProductId())
                .build();

        return Price.create(params);
    }

    public String createStripeProductForAnimal(Animal animal) throws StripeException {
        Stripe.apiKey = stripeConfig.getSecretKey();

        ProductCreateParams params = ProductCreateParams.builder()
                .setName(animal.getNombre()) // O cualquier atributo identificador del animal
                .setDescription("Apadrinamiento de " + animal.getNombre())
                .build();

        Product product = Product.create(params);
        animal.setStripeProductId(product.getId());
        animalService.save(animal);
        return product.getId();
    }

    public boolean cancelarSuscripcion(String subscriptionId) {
        Stripe.apiKey = stripeConfig.getSecretKey();

        try {
            com.stripe.model.Subscription subscription = com.stripe.model.Subscription.retrieve(subscriptionId);
            subscription.cancel();
            return true;
        } catch (StripeException e) {
            e.printStackTrace();
            return false;
        }
    }
}
