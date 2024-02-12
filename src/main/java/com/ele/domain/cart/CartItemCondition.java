package com.ele.domain.cart;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class CartItemCondition {

    @Id @GeneratedValue
    @Column(name = "cart_item_condition_id")
    private Long id;
    private String color;
    private String size;
    private int count;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;

    @Builder
    public CartItemCondition(String color, String size, int count) {
        this.color = color;
        this.size = size;
        this.count = count;
    }

    protected CartItemCondition() {
    }

    public void createCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

}
