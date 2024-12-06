package com.neoleaptask.NeoleapTask.service;

import com.neoleaptask.NeoleapTask.dto.OrderRequestDto;
import com.neoleaptask.NeoleapTask.dto.OrderResponseDto;
import com.neoleaptask.NeoleapTask.dto.PaymentResponseDto;
import com.neoleaptask.NeoleapTask.model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order getOrderById(Long id);
    List<OrderResponseDto> getAllOrders();
    OrderResponseDto createOrder(OrderRequestDto orderDto);
    OrderResponseDto updateOrder(Long id, Order updatedOrder);
    void deleteOrder(Long id);
    PaymentResponseDto createPayment(Long id, BigDecimal amount);
}
