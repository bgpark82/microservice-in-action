package com.bgpark.item.domain.product.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionGroupCreateDto {

    private String name;
    private List<OptionCreateDto> options = new ArrayList<>();
}
