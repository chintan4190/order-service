package com.example.demo.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessCodeRequest {
    private String cardNumber;
    private String passNumber;
    private int accessCode;

}
