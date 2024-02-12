package com.ele.response.item;

import com.ele.domain.item.Item;
import com.ele.domain.item.ItemCondition;
import com.ele.dto.item.ItemConditionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class ItemDetailResponse {

    private String itemName;
    private int price;
    private List<String> colorList;
    private List<String> sizeList;
    private List<ItemConditionDto> itemConditionDtos;

    public static ItemDetailResponse toSave(Item item) {

        ItemDetailResponse response = new ItemDetailResponse();
        response.setItemName(item.getItemName());
        response.setPrice(item.getPrice());
        List<ItemConditionDto> itemConditionDtoList = new ArrayList<>();
        List<String> colorList = new ArrayList<>();
        List<String> sizeList = new ArrayList<>();
        for(ItemCondition itemCondition : item.getItemConditions()) {
            String color = itemCondition.getColor();
            String size = itemCondition.getSize();
            colorList.add(color);
            sizeList.add(size);

            ItemConditionDto dto = new ItemConditionDto();
            dto.setColor(itemCondition.getColor());
            dto.setSize(itemCondition.getSize());
            dto.setQuantity(itemCondition.getQuantity());
            itemConditionDtoList.add(dto);
        }
        colorList = colorList.stream().distinct().collect(Collectors.toList());
        sizeList = sizeList.stream().distinct().collect(Collectors.toList());
        response.setColorList(colorList);
        response.setSizeList(sizeList);
        response.setItemConditionDtos(itemConditionDtoList);
        return response;
    }
}
