package com.ele.domain.order;

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
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;
    @OneToMany
    @JoinColumn(name = "item_id")
    private Item item;
    @OneToMany(mappedBy = "orderItem", cascade = ALL, orphanRemoval = true)
    private List<OrderItemCondition> orderItemConditions = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    private int orderPrice;

    @Builder
    public OrderItem(Item item, List<OrderItemCondition> orderItemConditions, int orderPrice) {
        for(OrderItemCondition orderItemCondition : orderItemConditions) {
            addOrderItemCondition(orderItemCondition);
            item.minusQuantity(orderItemCondition, orderItemCondition.getCount());
        }
        this.item = item;
        this.orderPrice = orderPrice;
    }

    protected OrderItem() {
    }

    public void addOrderItemCondition(OrderItemCondition condition) {
        this.orderItemConditions.add(condition);
        condition.createOrderItem(this);
    }


}
