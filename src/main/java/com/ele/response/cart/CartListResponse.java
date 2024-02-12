package com.ele.response.cart;

import com.ele.domain.cart.CartItem;
import com.ele.domain.cart.CartItemCondition;
import com.ele.dto.item.CartItemConditionDto;
import com.ele.dto.item.CartItemDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CartListResponse {

    private List<CartItemDto> cartItemDtos;

    public static CartListResponse toSave(List<CartItem> cartItems) {

        CartListResponse response = new CartListResponse();
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        for(CartItem cartItem : cartItems) {
            CartItemDto dto = new CartItemDto();
            dto.setItemName(cartItem.getItem().getItemName());
            dto.setPrice(cartItem.getItem().getPrice());
            List<CartItemConditionDto> conditionDtos = new ArrayList<>();
            for (CartItemCondition condition : cartItem.getCartItemConditions()) {
                CartItemConditionDto conditionDto = new CartItemConditionDto();
                conditionDto.setColor(condition.getColor());
                conditionDto.setSize(condition.getSize());
                conditionDto.setCount(condition.getCount());
                conditionDto.setTotalPrice(cartItem.getItem().getPrice() * condition.getCount());
                conditionDtos.add(conditionDto);
            }
            dto.setCartItemConditionDtos(conditionDtos);
            cartItemDtoList.add(dto);
        }
        response.setCartItemDtos(cartItemDtoList);
        return response;
    }
}
