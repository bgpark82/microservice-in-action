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
public class OptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // joinColumn will tell who is owner of relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // no mappedBy will create extra table for One to Many mapping, option will map with OptionGroup by getGroup()
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "group")
    private List<Option> options = new ArrayList();

    public static OptionGroup create(String name) {
        OptionGroup group = new OptionGroup();
        group.name = name;
        return group;
    }

    public void addItem(Item item) {
        this.item = item;
    }

    public void addOptions(List<Option> options) {
        this.options = options;
        this.options.stream().forEach(option -> option.addGroup(this));
    }
}
