package com.neoleaptask.NeoleapTask.service.impl;

import com.neoleaptask.NeoleapTask.dto.OrderRequestDto;
import com.neoleaptask.NeoleapTask.mapper.OrderMapper;
import com.neoleaptask.NeoleapTask.model.Order;
import com.neoleaptask.NeoleapTask.repository.OrderRepository;
import com.neoleaptask.NeoleapTask.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order createOrder(OrderRequestDto orderDto) {
        Order orderEntity = OrderMapper.toOrder(orderDto);
        return orderRepository.save(orderEntity);
    }

    @Override
    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = getOrderById(id);
//        existingOrder.setDescription(updatedOrder.getDescription());
//        existingOrder.setAmount(updatedOrder.getAmount());
//        existingOrder.setStatus(updatedOrder.getStatus());
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}
