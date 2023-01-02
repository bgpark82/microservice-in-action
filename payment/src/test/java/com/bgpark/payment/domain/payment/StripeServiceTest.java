package com.bgpark.payment.domain.payment;

import com.stripe.model.Price;
import com.stripe.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class StripeServiceTest {

    @Autowired
    private StripeService stripeService;

    @Test
    void createProduct() {
        Product product = stripeService.createProduct();
        assertAll(
                // () -> assertThat(product.getId()).isEqualTo("prod_N5rXYp6tPyWH8W"), id changes every requests
                () -> assertThat(product.getDescription()).isEqualTo("$12/Month subscription"),
                () -> assertThat(product.getName()).isEqualTo("Starter Subscription"),
                () -> assertThat(product.getObject()).isEqualTo("product"),
                () -> assertThat(product.getType()).isEqualTo("service"),
                () -> assertThat(product.getActive()).isTrue(),
                () -> assertThat(product.getAttributes()).isEmpty(),
                () -> assertThat(product.getCaption()).isNull(),
                () -> assertThat(product.getDefaultPrice()).isNull(),
                () -> assertThat(product.getDeleted()).isNull(),
                () -> assertThat(product.getImages()).isEmpty(),
                () -> assertThat(product.getLivemode()).isFalse(),
                () -> assertThat(product.getMetadata()).isEmpty(),
                () -> assertThat(product.getPackageDimensions()).isNull(),
                () -> assertThat(product.getShippable()).isNull(),
                () -> assertThat(product.getStatementDescriptor()).isNull(),
                () -> assertThat(product.getTaxCode()).isNull(),
                () -> assertThat(product.getUnitLabel()).isNull(),
                () -> assertThat(product.getUrl()).isNull()
        );
    }

    @Test
    void createPrice() {
        Product product = stripeService.createProduct();
        Price price = stripeService.createPrice(product);
        assertAll(
                () -> assertThat(price.getId()).isNotNull(),
                () -> assertThat(price.getActive()).isTrue(),
                () -> assertThat(price.getBillingScheme()).isEqualTo("per_unit"),
                () -> assertThat(price.getCurrency()).isEqualTo("usd"),
                () -> assertThat(price.getCurrencyOptions()).isNull(),
                () -> assertThat(price.getCustomUnitAmount()).isNull(),
                () -> assertThat(price.getDeleted()).isNull(),
                () -> assertThat(price.getLivemode()).isFalse(),
                () -> assertThat(price.getLookupKey()).isNull(),
                () -> assertThat(price.getMetadata()).isEmpty(),
                () -> assertThat(price.getNickname()).isNull(),
                () -> assertThat(price.getObject()).isEqualTo("price"),
                () -> assertThat(price.getProduct()).isEqualTo(product.getId()),
                () -> assertThat(price.getTaxBehavior()).isEqualTo("unspecified"),
                () -> assertThat(price.getTiers()).isNull(),
                () -> assertThat(price.getTiersMode()).isNull(),
                () -> assertThat(price.getTransformQuantity()).isNull(),
                () -> assertThat(price.getType()).isEqualTo("recurring"),
                () -> assertThat(price.getUnitAmount()).isEqualTo(1200),
                () -> assertThat(price.getUnitAmountDecimal()).isEqualTo(BigDecimal.valueOf(1200)),
                () -> assertThat(price.getRecurring().getAggregateUsage()).isNull(),
                () -> assertThat(price.getRecurring().getInterval()).isEqualTo("month"),
                () -> assertThat(price.getRecurring().getIntervalCount()).isEqualTo(1),
                () -> assertThat(price.getRecurring().getTrialPeriodDays()).isNull(),
                () -> assertThat(price.getRecurring().getUsageType()).isEqualTo("licensed")
        );
    }
}
