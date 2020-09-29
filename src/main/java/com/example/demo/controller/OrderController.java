package com.example.demo.controller;

import com.example.demo.message.CancelOrderRequest;
import com.example.demo.message.CancelOrderResponse;
import com.example.demo.message.ConfirmOrderRequest;
import com.example.demo.message.ConfirmOrderResponse;
import com.example.demo.message.CreateOrderRequest;
import com.example.demo.message.CreateOrderResponse;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create_order")
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest){
        return orderService.createOrder(createOrderRequest);
    }

    @PostMapping("/confirm_order")
    public ConfirmOrderResponse confirmOrder(@RequestBody ConfirmOrderRequest confirmOrderRequest){
        return orderService.confirmOrder(confirmOrderRequest);
    }

    @PostMapping("/cancel_order")
    public CancelOrderResponse cancelOrder(@RequestBody CancelOrderRequest cancelOrderRequest){
        return orderService.cancelOrder(cancelOrderRequest);
    }

}
