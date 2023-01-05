package com.bgpark.order.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private Long id;
    private String name;
    private int price;
    private int amount;
    private List<OrderOption> options;

    public int calculatePrice() {
        return calculateItemPrice() + calculateOptionsPrice();
    }

    private int calculateOptionsPrice() {
        return options.stream()
                .mapToInt(OrderOption::calculatePrice)
                .sum();
    }

    private int calculateItemPrice() {
        return price * amount;
    }
}
