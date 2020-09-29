package com.example.demo.message;

import lombok.Data;

@Data
public class ConfirmOrderRequest {
        private Long orderId;
        private int accessCode;
}
