package com.ele.repository.cart;

import com.ele.domain.cart.CartItem;
import com.ele.domain.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByItem(Item item);

}
