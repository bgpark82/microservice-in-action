package com.bgpark.order.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsDto {

    private List<OrderItemDto> items;

    public OrderItems toOrderItem() {
        return OrderItems.of(items.stream().map(item -> OrderItem.builder()
                .name(item.getItemName())
                .price(item.getItemPrice())
                .amount(item.getItemAmount())
                .options(item.getOptions().stream()
                        .map(option -> OrderOption.builder()
                                .name(option.getOptionName())
                                .price(option.getOptionPrice())
                                .amount(option.getOptionAmount())
                                .build())
                        .collect(Collectors.toList()))
                .build()
        ).collect(Collectors.toList())) ;
    }
}
