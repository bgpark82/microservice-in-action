package com.bgpark.order.domain.order;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OrderStatus status;
    private int price;

    public static Order create(int price) {
        Order order = new Order();
        order.status = OrderStatus.READY;
        order.price = price;
        return order;
    }

    enum OrderStatus {
        READY
    }
}
