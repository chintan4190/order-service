package com.example.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class OrderServiceProperty {

    @Value("${card-info-service.uri}")
    private String cardInfoServiceUri;

    @Value("${balance-service.uri}")
    private String balanceServiceUri;

    @Value("${access-code-service.uri}")
    private String accessCodeServiceUri;

}
