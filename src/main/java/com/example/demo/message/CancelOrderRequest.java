package com.example.demo.message;

import lombok.Data;

@Data
public class CancelOrderRequest {
        private Long orderId;
        private int accessCode;
}
