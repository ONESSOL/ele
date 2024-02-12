package com.ele.domain.item;

import com.ele.domain.order.OrderItemCondition;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String itemName;
    private int price;
    @Enumerated(STRING)
    private ItemCode itemCode;
    @OneToMany(mappedBy = "item", cascade = ALL, orphanRemoval = true)
    private List<ItemCondition> itemConditions = new ArrayList<>();

    @Builder
    public Item(String itemName, int price, ItemCode itemCode, List<ItemCondition> itemConditions) {
        this.itemName = itemName;
        this.price = price;
        this.itemCode = itemCode;
        for(ItemCondition itemCondition : itemConditions) {
            addItemCondition(itemCondition);
        }
    }

    protected Item() {
    }

    public void addItemCondition(ItemCondition itemCondition) {
        this.itemConditions.add(itemCondition);
        itemCondition.createItem(this);
    }

    public void update(String itemName, int price, ItemCode itemCode) {
        this.itemName = itemName;
        this.price = price;
        this.itemCode = itemCode;
    }

    public void minusQuantity(OrderItemCondition orderItemCondition, int count) {
        for(ItemCondition itemCondition : this.itemConditions) {
            if(orderItemCondition.getOrderItem().getItem().equals(itemCondition.getItem())
            && orderItemCondition.getColor().equals(itemCondition.getColor())
            && orderItemCondition.getSize().equals( itemCondition.getSize())) {
                itemCondition.minusQuantity(count);
            }
        }
    }
}


























