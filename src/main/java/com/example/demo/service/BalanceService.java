package com.example.demo.service;

import com.example.demo.config.OrderServiceProperty;
import com.example.demo.message.GetBalanceRequest;
import com.example.demo.message.GetBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final RestTemplate restTemplate;
    private final OrderServiceProperty orderServiceProperty;

    public int getBalance(GetBalanceRequest request){
        GetBalanceResponse response = restTemplate.getForObject(orderServiceProperty.getBalanceServiceUri(), GetBalanceResponse.class);
        return response.getAmount();
    }

}
