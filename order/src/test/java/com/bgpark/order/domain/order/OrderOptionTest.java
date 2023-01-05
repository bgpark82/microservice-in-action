package com.bgpark.order.domain.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderOptionTest {

    @Test
    void calculatePrice() {
        OrderOption option = createDDayCalendar();
        assertThat(option.calculatePrice()).isEqualTo(32_000);
    }

    private static OrderOption createDDayCalendar() {
        return OrderOption.builder()
                .price(16_000)
                .name("디데이 달력")
                .amount(2)
                .build();
    }
}
