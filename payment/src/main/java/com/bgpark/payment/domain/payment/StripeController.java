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

    private static final String PAYMENT_SUCCESS_URL = "http://localhost:3000/payment/success";
    private static final String PAYMENT_CANCEL_URL = "http://localhost:3000/payment/cancel";

    @PostMapping("/create-checkout-session")
    public ResponseEntity<String> checkout() {
        Stripe.apiKey = "sk_test_51MLfW8KswDDFJov0KbxEsnLmgfleRVq6pS1uhEYIZoBVMv0JJj2dsaA464SFJvSoyOop0TFVZqj3raT81qT9zcIs00gjxDOaaP";
        SessionCreateParams params = SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(PAYMENT_SUCCESS_URL)
                        .setCancelUrl(PAYMENT_CANCEL_URL)
                        .addLineItem(createLineItem())
                        .build();

        try {
            Session session = Session.create(params);
            return ResponseEntity.ok(session.getUrl());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

    }

    private static SessionCreateParams.LineItem createLineItem() {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(createPriceData())
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData createPriceData() {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount(2000L)
                .setProductData(createProductData())
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData.ProductData createProductData() {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName("T-shirt")
                .build();
    }
}
