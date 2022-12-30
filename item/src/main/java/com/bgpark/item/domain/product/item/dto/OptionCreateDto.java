package com.bgpark.item.domain.product.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionCreateDto {

    private Long id;
    private String name;
    private int price;
    private int amount;
}
