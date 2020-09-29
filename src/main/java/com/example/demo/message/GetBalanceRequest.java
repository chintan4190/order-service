package com.example.demo.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetBalanceRequest {
    private String cardNumber;
    private String passNumber;
}
