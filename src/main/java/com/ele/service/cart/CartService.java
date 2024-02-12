package com.ele.service.cart;

import com.ele.domain.cart.CartItem;
import com.ele.domain.cart.CartItemCondition;
import com.ele.domain.item.Item;
import com.ele.domain.member.Member;
import com.ele.exception.item.ItemNotFoundException;
import com.ele.exception.member.MemberNotFoundException;
import com.ele.repository.cart.CartItemRepository;
import com.ele.repository.item.ItemRepository;
import com.ele.repository.member.MemberRepository;
import com.ele.request.cart.CartCreateRequest;
import com.ele.request.cart.CartItemUpdateRequest;
import com.ele.response.cart.CartCreateResponse;
import com.ele.response.cart.CartItemUpdateResponse;
import com.ele.response.cart.CartListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public CartCreateResponse saveCart(Long memberId, CartCreateRequest request) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(ItemNotFoundException::new);

        CartItem findCartItem = cartItemRepository.findByItem(item).orElse(null);
        if (findCartItem != null) {
            for (CartItemCondition cartItemCondition : request.getCartItemConditions()) {
                findCartItem.addCartItemCondition(cartItemCondition);
            }
            return CartCreateResponse.toSave(findCartItem);
        } else {
            CartItem cartItem = cartItemRepository.save(CartItem.builder()
                    .item(item)
                    .cart(member.getCart())
                    .cartItemConditions(request.getCartItemConditions())
                    .build());
            return CartCreateResponse.toSave(cartItem);
        }
    }

    @Transactional(readOnly = true)
    public CartListResponse myCart(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        List<CartItem> cartItems = member.getCart().getCartItems();
        return CartListResponse.toSave(cartItems);
    }

    @Transactional
    public CartItemUpdateResponse update(Long cartItemId, CartItemUpdateRequest request) {

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(ItemNotFoundException::new);
        cartItem.getCartItemConditions().removeAll(cartItem.getCartItemConditions());
        for(CartItemCondition condition : request.getCartItemConditions()) {
            CartItemCondition cartItemCondition = CartItemCondition.builder()
                    .color(condition.getColor())
                    .size(condition.getSize())
                    .count(condition.getCount())
                    .build();
            cartItem.addCartItemCondition(cartItemCondition);
        }
        return CartItemUpdateResponse.toSave(cartItem);
    }

    @Transactional
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}




















