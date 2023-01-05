package com.bgpark.order.domain.order;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderItems {

    private List<OrderItem> items;

    public static OrderItems of(List<OrderItem> items) {
        OrderItems orderItems = new OrderItems();
        orderItems.items = items;
        return orderItems;
    }

    public int calculatePrice() {
        return items.stream()
                .mapToInt(OrderItem::calculatePrice)
                .sum();
    }
}
