package com.ele.service.item;

import com.ele.domain.item.Item;
import com.ele.domain.item.ItemCode;
import com.ele.domain.item.ItemCondition;
import com.ele.exception.item.ItemNotFoundException;
import com.ele.repository.item.ItemRepository;
import com.ele.request.item.ItemCreateRequest;
import com.ele.request.item.ItemUpdateRequest;
import com.ele.response.item.ItemCreateResponse;
import com.ele.response.item.ItemDetailResponse;
import com.ele.response.item.ItemListResponse;
import com.ele.response.item.ItemUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public ItemCreateResponse saveItem(ItemCreateRequest request) {

        Item item = itemRepository.save(Item.builder()
                .itemName(request.getItemName())
                .price(request.getPrice())
                .itemCode(request.getItemCode())
                .itemConditions(request.getItemConditions())
                .build());

        return ItemCreateResponse.toSave(item);
    }

    @Transactional(readOnly = true)
    public ItemDetailResponse findById(Long id) {

        Item item = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        return ItemDetailResponse.toSave(item);
    }

    @Transactional(readOnly = true)
    public Page<ItemListResponse> findByItemCode(ItemCode itemCode, Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<Item> itemList = itemRepository.findByItemCode(itemCode, pageRequest);
        return itemList.map(ItemListResponse::toSave);
    }

    @Transactional
    public Page<ItemListResponse> findByItemName(String itemName, Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<Item> itemList = itemRepository.findByItemNameContaining(itemName, pageRequest);
        return itemList.map(ItemListResponse::toSave);
    }

    @Transactional
    public ItemUpdateResponse update(Long itemId, ItemUpdateRequest request) {

        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        item.getItemConditions().removeAll(item.getItemConditions());
        for(ItemCondition itemCondition : request.getItemConditions()) {
            item.addItemCondition(itemCondition);
        }
        item.update(request.getItemName(), request.getPrice(), request.getItemCode());
        return ItemUpdateResponse.toSave(item);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}























