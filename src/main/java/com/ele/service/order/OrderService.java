package com.ele.service.order;

import com.ele.domain.delivery.Delivery;
import com.ele.domain.item.Item;
import com.ele.domain.member.Member;
import com.ele.domain.order.Order;
import com.ele.domain.order.OrderItem;
import com.ele.domain.order.OrderItemCondition;
import com.ele.exception.item.ItemNotFoundException;
import com.ele.exception.member.MemberNotFoundException;
import com.ele.repository.delivery.DeliveryRepository;
import com.ele.repository.item.ItemRepository;
import com.ele.repository.member.MemberRepository;
import com.ele.repository.order.OrderItemRepository;
import com.ele.repository.order.OrderRepository;
import com.ele.request.order.OrderSimpleCreateRequest;
import com.ele.response.order.OrderSimpleCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public OrderSimpleCreateResponse saveSimpleOrder(Long memberId, OrderSimpleCreateRequest request) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(ItemNotFoundException::new);

        Delivery delivery = deliveryRepository.save(
                Delivery.builder()
                        .address(member.getAddress())
                        .build());

        OrderItem.builder()
                .item(item)
                .orderItemConditions(request.getOrderItemConditions())
                .orderPrice()
                .build()
    }


}





































