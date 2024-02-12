package com.ele.domain.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class ItemCondition {

    @Id @GeneratedValue
    @Column(name = "item_condition_id")
    private Long id;
    private String color;
    private String size;
    private int quantity;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public ItemCondition(String color, String size, int quantity) {
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }

    protected ItemCondition() {
    }

    public void createItem(Item item) {
        this.item = item;
    }

    public void update(String color, String size, int quantity) {
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }

    public void minusQuantity(int count) {
        this.quantity -= count;
    }
}

















