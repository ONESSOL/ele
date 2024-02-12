package com.ele.response.order;

import com.ele.domain.order.Order;
import com.ele.domain.order.OrderItem;
import com.ele.domain.order.OrderItemCondition;
import com.ele.dto.item.OrderItemDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderSimpleCreateResponse {

    private String itemName;
    private int price;
    private List<OrderItemDto> orderItemDtos;

    public static OrderSimpleCreateResponse toSave(Order order) {

        OrderSimpleCreateResponse response = new OrderSimpleCreateResponse();
        response.setItemName(order.getOrderItems().get(0).getItem().getItemName());
        response.setPrice(order.getOrderItems().get(0).getItem().getPrice());
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()) {
            OrderItemDto dto = new OrderItemDto();
            for(OrderItemCondition orderItemCondition : orderItem.getOrderItemConditions()) {
                dto.setColor(orderItemCondition.getColor());
                dto.setSize(orderItemCondition.getSize());
                dto.setCount(orderItemCondition.getCount());
            }
            orderItemDtoList.add(dto);
        }
        response.setOrderItemDtos(orderItemDtoList);
        return response;
    }

}
