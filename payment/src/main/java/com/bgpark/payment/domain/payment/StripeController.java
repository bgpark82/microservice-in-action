package com.bgpark.payment.domain.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StripeController {

    @PostMapping("/create-checkout-session")
    public ResponseEntity<String> checkout() {
        Stripe.apiKey = "sk_test_51MLfW8KswDDFJov0KbxEsnLmgfleRVq6pS1uhEYIZoBVMv0JJj2dsaA464SFJvSoyOop0TFVZqj3raT81qT9zcIs00gjxDOaaP";
        SessionCreateParams params = SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:3000/success")
                        .setCancelUrl("http://localhost:3000/cancel")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("usd")
                                                        .setUnitAmount(2000L)
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("T-shirt")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build();

        Session session = null;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(session.getUrl());
    }
}
