package com.example.demo.service;

import com.example.demo.config.OrderServiceProperty;
import com.example.demo.message.AccessCodeRequest;
import com.example.demo.message.AccessCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AccessCodeVerifierService {

    private final RestTemplate restTemplate;
    private final OrderServiceProperty orderServiceProperty;

    public boolean isAccessCodeValid(AccessCodeRequest request){
        AccessCodeResponse codeResponse = restTemplate.getForObject(orderServiceProperty.getAccessCodeServiceUri(), AccessCodeResponse.class);
        return codeResponse.getIsValid();
    }
}
