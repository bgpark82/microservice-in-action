package com.bgpark.item.domain.product.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private OptionGroup group;

    public static Option create(String name, int price, int amount) {
        Option option = new Option();
        option.name = name;
        option.price = price;
        option.amount = amount;
        return option;
    }

    public void addGroup(OptionGroup group) {
        this.group = group;
    }
}
