package com.ele.domain.cart;

import com.ele.domain.item.Item;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "`cart`_id")
    private Cart cart;
    @OneToMany(mappedBy = "cartItem", cascade = ALL, orphanRemoval = true)
    private List<CartItemCondition> cartItemConditions = new ArrayList<>();

    @Builder
    public CartItem(Item item, Cart cart, List<CartItemCondition> cartItemConditions) {
        this.item = item;
        createCart(cart);
        for(CartItemCondition cartItemCondition : cartItemConditions) {
            addCartItemCondition(cartItemCondition);
        }
    }

    protected CartItem() {
    }

    public void createCart(Cart cart) {
        this.cart = cart;
        cart.addCartItem(this);
    }

    public void addCartItemCondition(CartItemCondition cartItemCondition) {
        this.cartItemConditions.add(cartItemCondition);
        cartItemCondition.createCartItem(this);
    }
}



































