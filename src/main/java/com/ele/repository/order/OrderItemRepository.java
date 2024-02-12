package com.ele.repository.order;

import com.ele.domain.item.Item;
import com.ele.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findByItem(Item item);
}
