package com.neoleaptask.NeoleapTask.mapper;

import com.neoleaptask.NeoleapTask.dto.OrderRequestDto;
import com.neoleaptask.NeoleapTask.enums.OrderStatus;
import com.neoleaptask.NeoleapTask.model.Order;
import org.springframework.stereotype.Component;

public class OrderMapper {

    public static Order toOrder(OrderRequestDto dto) {
        Order order = new Order();
        order.setDescription(dto.getDescription());
        order.setAmount(dto.getAmount());

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
}
