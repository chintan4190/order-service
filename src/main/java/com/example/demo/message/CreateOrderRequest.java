package com.example.demo.message;

import lombok.Data;

@Data
public class CreateOrderRequest {

    private String cardNumber;
    private String passNumber;
    private int amount;

}
