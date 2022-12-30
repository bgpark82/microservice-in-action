package com.bgpark.item.domain.product.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private int amount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "item")
    private List<OptionGroup> groups = new ArrayList<>();

    public static Item create(String name, int price, int amount) {
        Item item = new Item();
        item.name = name;
        item.price = price;
        item.amount = amount;
        return item;
    }

    public void addGroup(List<OptionGroup> groups) {
        if (this.groups == null) {
            this.groups = new ArrayList<>();
        }
        this.groups.addAll(groups);
        groups.forEach(group -> group.addItem(this));
    }
}
