package com.example.demo.message;

import com.example.demo.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CancelOrderResponse {
    private OrderStatus orderStatus;
}
