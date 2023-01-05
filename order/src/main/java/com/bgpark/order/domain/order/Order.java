package com.bgpark.order.domain.order;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Entity
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
