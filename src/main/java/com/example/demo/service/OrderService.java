package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.error.OrderException;
import com.example.demo.message.AccessCodeRequest;
import com.example.demo.message.CancelOrderRequest;
import com.example.demo.message.CancelOrderResponse;
import com.example.demo.message.CardInfoRequest;
import com.example.demo.message.CardInfoResponse;
import com.example.demo.message.ConfirmOrderRequest;
import com.example.demo.message.ConfirmOrderResponse;
import com.example.demo.message.CreateOrderRequest;
import com.example.demo.message.CreateOrderResponse;
import com.example.demo.message.GetBalanceRequest;
import com.example.demo.repository.OrderRepository;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CardService cardService;
    private final AccessCodeVerifierService accessCodeVerifierService;
    private final BalanceService balanceService;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        log.info("creating order for {}, {} ", request.getCardNumber(), request.getPassNumber());
        validateRequest(request);
        CardInfoResponse cardInfoResponse = cardService.getCardInfo(new CardInfoRequest(request.getCardNumber(), request.getPassNumber()));
        Order order = generateOrder(request, cardInfoResponse);
        Order savedOrder = orderRepository.save(order);

        return CreateOrderResponse.builder()
                .creationTime(order.getCreatedDate())
                .summary(String.format("Order for %s is created of amount %d", order.getName(), order.getAmount()))
                .orderStatus(savedOrder.getOrderStatus())
                .orderId(savedOrder.getOrderId())
                .build();
    }

    private Order generateOrder(CreateOrderRequest createOrderRequest, CardInfoResponse cardInfoResponse) {
        Order order = new Order();
        order.setPassNumber(createOrderRequest.getPassNumber());
        order.setCardNumber(createOrderRequest.getCardNumber());
        order.setAmount(createOrderRequest.getAmount());
        order.setCreatedDate(Instant.now());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setName(cardInfoResponse.getName());
        order.setGender(cardInfoResponse.getGender());
        return order;
    }

    private void validateRequest(CreateOrderRequest createOrderRequest) {
        if (createOrderRequest.getCardNumber() == null || createOrderRequest.getPassNumber() == null) {
            throw new OrderException("Account details can not be null");
        }
    }

    @Transactional
    public ConfirmOrderResponse confirmOrder(ConfirmOrderRequest confirmOrderRequest) {

        Order order = findOrder(confirmOrderRequest.getOrderId());
        isValidAccessCode(order.getCardNumber(), order.getPassNumber(), confirmOrderRequest.getAccessCode());

        int availableBalance = balanceService.getBalance(GetBalanceRequest.builder()
                .cardNumber(order.getCardNumber())
                .passNumber(order.getPassNumber())
                .build());
        if (availableBalance < order.getAmount()) {
            throw new OrderException("Insufficient fund");
        }
        return new ConfirmOrderResponse(OrderStatus.CONFIRMED);

    }

    private Order findOrder(Long orderId) {
        Optional<Order> byId = orderRepository.findById(orderId);
        return byId.orElseThrow(() -> new OrderException("order does not exist with id " + orderId));
    }

    private void isValidAccessCode(String cardNo, String passNo, int accessCode) {
        boolean isValid = accessCodeVerifierService.isAccessCodeValid(AccessCodeRequest.builder()
                .accessCode(accessCode)
                .cardNumber(cardNo)
                .passNumber(passNo)
                .build());
        if (!isValid) {
            throw new OrderException("Invalid Access code");
        }
    }

    @Transactional
    public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) {
        Order order = findOrder(cancelOrderRequest.getOrderId());
        isValidAccessCode(order.getCardNumber(), order.getPassNumber(), cancelOrderRequest.getAccessCode());
        order.setOrderStatus(OrderStatus.CANCELLED);
        return new CancelOrderResponse(OrderStatus.CANCELLED);
    }
}
