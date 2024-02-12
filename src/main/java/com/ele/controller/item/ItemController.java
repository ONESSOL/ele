package com.ele.controller.item;

import com.ele.domain.item.ItemCode;
import com.ele.request.item.ItemCreateRequest;
import com.ele.request.item.ItemUpdateRequest;
import com.ele.response.item.ItemCreateResponse;
import com.ele.response.item.ItemDetailResponse;
import com.ele.response.item.ItemListResponse;
import com.ele.response.item.ItemUpdateResponse;
import com.ele.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/save")
    public ResponseEntity<ItemCreateResponse> saveItem(@RequestBody ItemCreateRequest request) {
        return ResponseEntity.ok(itemService.saveItem(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @GetMapping("/list/{itemCode}")
    public ResponseEntity<Page<ItemListResponse>> findByItemCode(@PathVariable ItemCode itemCode,
                                                                 @PageableDefault(page = 1, sort = "id")Pageable pageable) {
        return ResponseEntity.ok(itemService.findByItemCode(itemCode, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ItemListResponse>> findByItemName(@RequestParam String itemName,
                                                                 @PageableDefault(page = 1, sort = "id")Pageable pageable) {
        return ResponseEntity.ok(itemService.findByItemName(itemName, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemUpdateResponse> update(@PathVariable Long id, @RequestBody ItemUpdateRequest request) {
        return ResponseEntity.ok(itemService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }
}




























