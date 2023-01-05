package com.bgpark.order.domain.order;

public interface ItemClient {

    OrderItemsDto order(OrderCreateDto request);
}
