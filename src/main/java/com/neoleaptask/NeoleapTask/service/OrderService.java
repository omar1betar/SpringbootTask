package com.neoleaptask.NeoleapTask.service;

import com.neoleaptask.NeoleapTask.dto.OrderRequestDto;
import com.neoleaptask.NeoleapTask.model.Order;

import java.util.List;

public interface OrderService {
    Order getOrderById(Long id);
    List<Order> getAllOrders();
    Order createOrder(OrderRequestDto orderDto);
    Order updateOrder(Long id, Order updatedOrder);
    void deleteOrder(Long id);
}
