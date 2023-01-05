package com.bgpark.order.domain.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final ItemClient itemClient;
    private final OrderRepository orderRepository;

    @PostMapping("/orders")
    public ResponseEntity<Order> create(OrderCreateDto request) {
        final OrderItemsDto itemsDto = itemClient.order(request);
        final OrderItems orderItems = itemsDto.toOrderItem();
        final int price = orderItems.calculatePrice();

        Order order = Order.create(price);

        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }
}
