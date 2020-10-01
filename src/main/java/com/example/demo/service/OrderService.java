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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CardService cardService;
    private final AccessCodeVerifierService accessCodeVerifierService;
    private final BalanceService balanceService;

    @Transactional
    public CreateOrderResponse createOrder(final CreateOrderRequest request) {
        log.info("creating order for {}, {} ", request.getCardNumber(), request.getPassNumber());
        validateRequest(request);
        final CardInfoResponse cardInfoResponse = this.cardService.getCardInfo(new CardInfoRequest(request.getCardNumber(), request.getPassNumber()));
        final Order order = generateOrder(request, cardInfoResponse);
        final Order savedOrder = this.orderRepository.save(order);

        return CreateOrderResponse.builder()
                .creationTime(order.getCreatedDate())
                .summary(String.format("Order for %s is created of amount %d", order.getName(), order.getAmount()))
                .orderStatus(savedOrder.getOrderStatus())
                .orderId(savedOrder.getOrderId())
                .build();
    }

    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    private Order generateOrder(final CreateOrderRequest createOrderRequest, final CardInfoResponse cardInfoResponse) {
        final Order order = new Order();
        order.setPassNumber(createOrderRequest.getPassNumber());
        order.setCardNumber(createOrderRequest.getCardNumber());
        order.setAmount(createOrderRequest.getAmount());
        order.setCreatedDate(Instant.now());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setName(cardInfoResponse.getName());
        order.setGender(cardInfoResponse.getGender());
        return order;
    }

    private void validateRequest(final CreateOrderRequest createOrderRequest) {
        if (createOrderRequest.getCardNumber() == null || createOrderRequest.getPassNumber() == null) {
            throw new OrderException("Account details can not be null");
        }
    }

    @Transactional
    public ConfirmOrderResponse confirmOrder(final ConfirmOrderRequest confirmOrderRequest) {

        final Order order = findOrder(confirmOrderRequest.getOrderId());
        isValidAccessCode(order.getCardNumber(), order.getPassNumber(), confirmOrderRequest.getAccessCode());

        final int availableBalance = this.balanceService.getBalance(GetBalanceRequest.builder()
                .cardNumber(order.getCardNumber())
                .passNumber(order.getPassNumber())
                .build());
        if (availableBalance < order.getAmount()) {
            throw new OrderException("Insufficient fund");
        }
        return new ConfirmOrderResponse(OrderStatus.CONFIRMED);

    }

    public Order findOrder(final Long orderId) {
        final Optional<Order> byId = this.orderRepository.findById(orderId);
        return byId.orElseThrow(() -> new OrderException("order does not exist with id " + orderId));
    }

    private void isValidAccessCode(final String cardNo, final String passNo, final int accessCode) {
        final boolean isValid = this.accessCodeVerifierService.isAccessCodeValid(AccessCodeRequest.builder()
                .accessCode(accessCode)
                .cardNumber(cardNo)
                .passNumber(passNo)
                .build());
        if (!isValid) {
            throw new OrderException("Invalid Access code");
        }
    }

    @Transactional
    public CancelOrderResponse cancelOrder(final CancelOrderRequest cancelOrderRequest) {
        final Order order = findOrder(cancelOrderRequest.getOrderId());
        isValidAccessCode(order.getCardNumber(), order.getPassNumber(), cancelOrderRequest.getAccessCode());
        order.setOrderStatus(OrderStatus.CANCELLED);
        return new CancelOrderResponse(OrderStatus.CANCELLED);
    }
}
