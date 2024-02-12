package com.ele.request.order;

import com.ele.domain.order.OrderItemCondition;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderSimpleCreateRequest {

    private Long itemId;
    private List<OrderItemCondition> orderItemConditions;

}
