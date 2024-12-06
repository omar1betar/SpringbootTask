package com.neoleaptask.NeoleapTask.mapper;

import com.neoleaptask.NeoleapTask.dto.OrderRequestDto;
import com.neoleaptask.NeoleapTask.dto.OrderResponseDto;
import com.neoleaptask.NeoleapTask.enums.OrderStatus;
import com.neoleaptask.NeoleapTask.model.Order;

public class OrderMapper {

    public static Order toOrder(OrderRequestDto dto) {
        Order order = new Order();
        order.setDescription(dto.getDescription());
        order.setAmount(dto.getAmount());
        order.setProductName(dto.getProductName());


        // Parse the status to the enum
        if (dto.getStatus() != null) {
            try {
                order.setStatus(OrderStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid status value: " + dto.getStatus());
            }
        }

        return order;
    }
    public static OrderResponseDto toOrderResponseDto(Order order){
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setDatabaseId(order.getId());
        orderResponseDto.setDescription(order.getDescription());
        orderResponseDto.setAmount(order.getAmount());
        orderResponseDto.setProductName(order.getProductName());
        orderResponseDto.setOrderId(order.getOrderId());
        orderResponseDto.setStatus(order.getStatus().toString());
        return orderResponseDto;
    }
}
