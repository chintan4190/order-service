package com.example.demo.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardInfoRequest {

    private String cardNumber;
    private String passNumber;
}
