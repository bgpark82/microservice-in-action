package com.bgpark.order.domain.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "item-service")
public interface ItemClient {

    @GetMapping("/item-service/items/{itemId}")
    OrderItemDto order(@PathVariable Long itemId);
}
