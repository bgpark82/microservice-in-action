package com.bgpark.order.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOption {

    private Long id;
    private String name;
    private int price;
    private int amount;

    public int calculatePrice() {
        return price * amount;
    }
}
