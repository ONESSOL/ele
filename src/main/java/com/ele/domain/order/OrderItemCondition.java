package com.ele.domain.order;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class OrderItemCondition {

    @Id @GeneratedValue
    @Column(name = "order_item_condition_id")
    private Long id;
    private String color;
    private String size;
    private int count;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    public OrderItemCondition(String color, String size, int count) {
        this.color = color;
        this.size = size;
        this.count = count;
    }

    protected OrderItemCondition() {
    }

    public void createOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
