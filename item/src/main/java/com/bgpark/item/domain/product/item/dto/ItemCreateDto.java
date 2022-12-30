package com.bgpark.item.domain.product.item.dto;

import com.bgpark.item.domain.product.item.Item;
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

    private List<OptionGroupCreateDto> groups = new ArrayList<>();

    public static ItemCreateDto create(Item item) {
        List<OptionGroupCreateDto> groups = item.getGroups().stream()
                .map(group -> OptionGroupCreateDto.builder()
                            .name(group.getName())
                            .options(group.getOptions().stream()
                                    .map(option -> OptionCreateDto.builder()
                                            .name(option.getName())
                                            .price(option.getPrice())
                                            .amount(option.getAmount())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build()
                ).collect(Collectors.toList());
        return ItemCreateDto.builder()
                .id(item.getId())
                .name(item.getName())
                .amount(item.getAmount())
                .price(item.getPrice())
                .groups(groups)
                .build();
    }
}
