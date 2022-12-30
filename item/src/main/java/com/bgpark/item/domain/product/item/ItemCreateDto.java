package com.bgpark.item.domain.product.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateDto {

    private String name;

    private int price;

    private int amount;

    private List<OptionGroupCreateDto> groups = new ArrayList<>();

    public static ItemCreateDto create(Item item) {
        List<OptionGroupCreateDto> groups = item.getGroups().stream()
                .map(group -> OptionGroupCreateDto.builder()
                        .name(group.getName())
                        .build())
                .collect(Collectors.toList());

        ItemCreateDto response = ItemCreateDto.builder()
                .name(item.getName())
                .amount(item.getAmount())
                .price(item.getPrice())
                .groups(groups)
                .build();

        return response;
    }

    public Item toItem() {
        Item item = createItem();
        List<OptionGroup> groups = createOptionGroup();
        item.addGroup(groups);
        return item;
    }

    @NotNull
    private List<OptionGroup> createOptionGroup() {
        return this.groups.stream()
                .map(group -> OptionGroup.builder()
                        .name(group.name)
                        .build())
                .collect(Collectors.toList());
    }

    private Item createItem() {
        return Item.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class OptionGroupCreateDto {
        private String name;
    }
}
