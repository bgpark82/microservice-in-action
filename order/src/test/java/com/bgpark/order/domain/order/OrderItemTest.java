package com.bgpark.order.domain.order;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @Test
    void calculatePrice() {
        OrderOption option = createDDayCalendar();
        OrderItem item = createBabyface(option);
        assertThat(item.calculatePrice()).isEqualTo(68_000);
    }

    private static OrderItem createBabyface(OrderOption option) {
        return OrderItem.builder()
                .price(36_000)
                .name("베이비페이스")
                .amount(1)
                .options(Arrays.asList(option))
                .build();
    }

    private static OrderOption createDDayCalendar() {
        return OrderOption.builder()
                .price(16_000)
                .name("디데이 달력")
                .amount(2)
                .build();
    }
}
