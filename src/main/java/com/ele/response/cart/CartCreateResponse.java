package com.ele.response.cart;

import com.ele.domain.cart.CartItem;
import com.ele.domain.cart.CartItemCondition;
import com.ele.dto.item.CartItemConditionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CartCreateResponse {

    private String itemName;
    private int price;
    private List<CartItemConditionDto> cartItemConditionDtos;

    public static CartCreateResponse toSave(CartItem cartItem) {

        CartCreateResponse response = new CartCreateResponse();
        response.setItemName(cartItem.getItem().getItemName());
        response.setPrice(cartItem.getItem().getPrice());
        List<CartItemConditionDto> cartItemConditionDtoList = new ArrayList<>();
        for(CartItemCondition cartItemCondition : cartItem.getCartItemConditions()) {
            CartItemConditionDto dto = new CartItemConditionDto();
            dto.setColor(cartItemCondition.getColor());
            dto.setSize(cartItemCondition.getSize());
            dto.setCount(cartItemCondition.getCount());
            dto.setTotalPrice(response.getPrice() * cartItemCondition.getCount());
            cartItemConditionDtoList.add(dto);
        }
        response.setCartItemConditionDtos(cartItemConditionDtoList);
        return response;
    }

}
