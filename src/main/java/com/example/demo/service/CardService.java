package com.example.demo.service;

import com.example.demo.config.OrderServiceProperty;
import com.example.demo.message.CardInfoRequest;
import com.example.demo.message.CardInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CardService {

    private final RestTemplate restTemplate;
    private final OrderServiceProperty orderServiceProperty;

    public CardInfoResponse getCardInfo(CardInfoRequest cardInfoRequest){
        return restTemplate.getForObject(orderServiceProperty.getCardInfoServiceUri(), CardInfoResponse.class);
    }
}
