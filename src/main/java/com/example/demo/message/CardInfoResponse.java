package com.example.demo.message;

import lombok.Data;

@Data
public class CardInfoResponse {
    private String cardNumber;
    private String passNumber;
    private String name;
    private String gender;
}
