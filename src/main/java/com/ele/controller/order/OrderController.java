package com.ele.controller.order;

import com.ele.config.SecurityUtil;
import com.ele.request.order.OrderSimpleCreateRequest;
import com.ele.response.order.OrderSimpleCreateResponse;
import com.ele.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/simple_save")
    public ResponseEntity<OrderSimpleCreateResponse> saveSimpleOrder(@RequestBody OrderSimpleCreateRequest request) {
        return ResponseEntity.ok(orderService.saveSimpleOrder(SecurityUtil.CurrentMemberId(), request));
    }
}



































