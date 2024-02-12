package com.ele.dto.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemConditionDto {

    private String color;
    private String size;
    private int count;
    private int totalPrice;

}
