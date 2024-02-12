package com.ele.request.cart;

import com.ele.domain.cart.CartItemCondition;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartCreateRequest {

    private Long itemId;
    private List<CartItemCondition> cartItemConditions;

}
