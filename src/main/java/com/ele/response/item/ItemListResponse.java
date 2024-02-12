package com.ele.response.item;

import com.ele.domain.item.Item;
import com.ele.domain.item.ItemCondition;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class ItemListResponse {

    private String itemName;
    private int price;
    private List<String> colors;

    public static ItemListResponse toSave(Item item) {

        ItemListResponse response = new ItemListResponse();
        response.setItemName(item.getItemName());
        response.setPrice(item.getPrice());
        List<String> colorList = new ArrayList<>();
        for(ItemCondition itemCondition : item.getItemConditions()) {
            colorList.add(itemCondition.getColor());
        }
        colorList = colorList.stream().distinct().collect(Collectors.toList());
        response.setColors(colorList);
        return response;
    }
}
