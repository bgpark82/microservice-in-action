package com.bgpark.item.domain.product.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSearchDto {

    private Long itemId;
    private String itemName;
    private int itemPrice;
    private int itemAmount;
    private List<OrderOptionDto> options;

    public static ItemSearchDto of(Item item) {
        List<OrderOptionDto> options = item.getGroups().stream()
                .map(group -> group.getOptions())
                .flatMap(Collection::stream)
                .map(option -> OrderOptionDto.builder()
                        .optionId(option.getId())
                        .optionName(option.getName())
                        .optionPrice(option.getPrice())
                        .optionAmount(option.getAmount())
                        .build())
                .collect(Collectors.toList());

        return ItemSearchDto.builder()
                .itemId(item.getId())
                .itemName(item.getName())
                .itemPrice(item.getPrice())
                .itemAmount(item.getAmount())
                .options(options)
                .build();
    }

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
