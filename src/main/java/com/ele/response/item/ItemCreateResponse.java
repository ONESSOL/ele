package com.ele.response.item;

import com.ele.domain.item.Item;
import com.ele.domain.item.ItemCode;
import com.ele.domain.item.ItemCondition;
import com.ele.dto.item.ItemConditionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemCreateResponse {

    private String itemName;
    private int price;
    private ItemCode itemCode;
    private List<ItemConditionDto> itemConditionDtos;

    public static ItemCreateResponse toSave(Item item) {

        ItemCreateResponse response = new ItemCreateResponse();
        response.setItemName(item.getItemName());
        response.setPrice(item.getPrice());
        response.setItemCode(item.getItemCode());
        List<ItemConditionDto> itemConditionDtoList = new ArrayList<>();
        for(ItemCondition itemCondition : item.getItemConditions()) {
            ItemConditionDto dto = new ItemConditionDto();
            dto.setColor(itemCondition.getColor());
            dto.setSize(itemCondition.getSize());
            dto.setQuantity(itemCondition.getQuantity());
            itemConditionDtoList.add(dto);
        }
        response.setItemConditionDtos(itemConditionDtoList);
        return response;
    }
}
