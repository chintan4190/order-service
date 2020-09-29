package com.example.demo.message;

import com.example.demo.entity.OrderStatus;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderResponse {
    private OrderStatus orderStatus;
    private Instant creationTime;
    private String summary;
    private Long orderId;
}
