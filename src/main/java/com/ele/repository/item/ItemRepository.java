package com.ele.repository.item;

import com.ele.domain.item.Item;
import com.ele.domain.item.ItemCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findByItemNameContaining(String itemName, Pageable pageable);
    Page<Item> findByItemCode(ItemCode itemCode, Pageable pageable);

}
