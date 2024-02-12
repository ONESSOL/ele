package com.ele.response.cart;

import com.ele.domain.cart.CartItem;
import com.ele.domain.cart.CartItemCondition;
import com.ele.dto.item.CartItemConditionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CartItemUpdateResponse {

    private String itemName;
    private int price;
    private List<CartItemConditionDto> cartItemConditionDtos;

    public static CartItemUpdateResponse toSave(CartItem cartItem) {

        CartItemUpdateResponse response = new CartItemUpdateResponse();
        response.setItemName(cartItem.getItem().getItemName());
        response.setPrice(cartItem.getItem().getPrice());
        List<CartItemConditionDto> cartItemConditionDtoList = new ArrayList<>();
        for(CartItemCondition cartItemCondition : cartItem.getCartItemConditions()) {
            CartItemConditionDto dto = new CartItemConditionDto();
            dto.setColor(cartItemCondition.getColor());
            dto.setSize(cartItemCondition.getSize());
            dto.setCount(cartItemCondition.getCount());
            dto.setTotalPrice(cartItem.getItem().getPrice() * cartItemCondition.getCount());
            cartItemConditionDtoList.add(dto);
        }
        response.setCartItemConditionDtos(cartItemConditionDtoList);
        return response;
    }

}

