package com.ele.dto.item;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartItemDto {

    private String itemName;
    private int price;
    private List<CartItemConditionDto> cartItemConditionDtos;

}
