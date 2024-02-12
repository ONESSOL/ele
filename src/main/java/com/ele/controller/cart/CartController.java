package com.ele.controller.cart;

import com.ele.config.SecurityUtil;
import com.ele.request.cart.CartCreateRequest;
import com.ele.request.cart.CartItemUpdateRequest;
import com.ele.response.cart.CartCreateResponse;
import com.ele.response.cart.CartItemUpdateResponse;
import com.ele.response.cart.CartListResponse;
import com.ele.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/save")
    public ResponseEntity<CartCreateResponse> saveCart(@RequestBody CartCreateRequest request) {
        return ResponseEntity.ok(cartService.saveCart(SecurityUtil.CurrentMemberId(), request));
    }

    @GetMapping("/my_cart")
    public ResponseEntity<CartListResponse> myCart() {
        return ResponseEntity.ok(cartService.myCart(SecurityUtil.CurrentMemberId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemUpdateResponse> update(@PathVariable Long id, @RequestBody CartItemUpdateRequest request) {
        return ResponseEntity.ok(cartService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartService.deleteCartItem(id);
        return ResponseEntity.ok().build();
    }
}




























