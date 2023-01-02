package com.bgpark.payment.domain.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StripeService {

    public Product createProduct() {
        Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";

        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName("Starter Subscription")
                .setDescription("$12/Month subscription")
                .build();
        try {
            Product product = Product.create(productParams);
            log.info(product.toJson());
            return product;
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    public Price createPrice(Product product) {
        PriceCreateParams params = PriceCreateParams
                        .builder()
                        .setProduct(product.getId())
                        .setCurrency("usd")
                        .setUnitAmount(1200L)
                        .setRecurring(
                                PriceCreateParams.Recurring
                                        .builder()
                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                        .build())
                        .build();
        try {
            Price price = Price.create(params);
            log.info(price.toJson());
            return price;
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}
