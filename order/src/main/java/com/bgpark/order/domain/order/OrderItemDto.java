package com.bgpark.order.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Long itemId;
    private String itemName;
    private int itemPrice;
    private int itemAmount;
    private List<OrderOptionDto> options;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class OrderOptionDto {
        private Long optionId;
        private String optionName;
        private int optionPrice;
        private int optionAmount;
    }

}
