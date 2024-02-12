package com.ele.request.item;

import com.ele.domain.item.ItemCode;
import com.ele.domain.item.ItemCondition;
import com.ele.dto.item.ItemConditionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ItemUpdateRequest {

    private String itemName;
    private int price;
    private ItemCode itemCode;
    private List<ItemCondition> itemConditions;

}
