package com.bgpark.item.domain.product.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateDto {

    private Long id;

    private String name;

    private int price;

    private int amount;

    public static ItemCreateDto create(Item item) {
        return ItemCreateDto.builder()
                .id(item.getId())
                .name(item.getName())
                .amount(item.getAmount())
                .price(item.getPrice())
                .build();
    }

    public Item toItem() {
        return Item.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .build();
    }
}
