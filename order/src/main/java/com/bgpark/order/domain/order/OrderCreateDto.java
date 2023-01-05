package com.bgpark.order.domain.order;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {

    private Long itemId;
    private int itemAmount;
    private List<OrderCreateOptionDto> options;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class OrderCreateOptionDto {
        private Long optionId;
        private int OptionAmount;
    }
}
