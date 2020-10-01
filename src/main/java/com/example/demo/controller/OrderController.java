package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.message.CancelOrderRequest;
import com.example.demo.message.CancelOrderResponse;
import com.example.demo.message.ConfirmOrderRequest;
import com.example.demo.message.ConfirmOrderResponse;
import com.example.demo.message.CreateOrderRequest;
import com.example.demo.message.CreateOrderResponse;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    public List<Order> list() {
        return this.orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order order(@PathVariable("id") final Long id) {
        return this.orderService.findOrder(id);
    }

    @PostMapping("/create")
    public CreateOrderResponse createOrder(@RequestBody final CreateOrderRequest createOrderRequest) {
        return this.orderService.createOrder(createOrderRequest);
    }

    @PostMapping("/confirm")
    public ConfirmOrderResponse confirmOrder(@RequestBody final ConfirmOrderRequest confirmOrderRequest) {
        return this.orderService.confirmOrder(confirmOrderRequest);
    }

    @PostMapping("/cancel")
    public CancelOrderResponse cancelOrder(@RequestBody final CancelOrderRequest cancelOrderRequest) {
        return this.orderService.cancelOrder(cancelOrderRequest);
    }

}
