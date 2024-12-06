package com.neoleaptask.NeoleapTask.controller;

import com.neoleaptask.NeoleapTask.dto.OrderRequestDto;
import com.neoleaptask.NeoleapTask.dto.PaymentResponseDto;
import com.neoleaptask.NeoleapTask.model.Order;
import com.neoleaptask.NeoleapTask.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CacheConfig(cacheNames = "orders")
public class OrderController {

    // Logger to log important information, warnings, and errors
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // Injecting the OrderService to handle business logic
    private final OrderService orderService;

    // Constructor to initialize the OrderService
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Validated OrderRequestDto orderDto) {
        // Call the service to create the order
        Order createdOrder = orderService.createOrder(orderDto);

        // Return a ResponseEntity with the created order and 201 Created status
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        // Log the request for debugging purposes
        logger.info("Fetching order with ID {}", id);

        // Get the order from the service
        Order order = orderService.getOrderById(id);

        // Return the order with 200 OK status
        return ResponseEntity.ok(order);
    }


    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        // Get all orders from the service
        List<Order> orders = orderService.getAllOrders();

        // Check if the list of orders is empty
        if (orders.isEmpty()) {
            // Return 204 No Content if no orders found
            return ResponseEntity.noContent().build();
        }

        // Return the list of orders with 200 OK status
        return ResponseEntity.ok(orders);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody @Validated Order updatedOrder) {
        // Call the service to update the order
        Order order = orderService.updateOrder(id, updatedOrder);

        // Return the updated order with 200 OK status
        return ResponseEntity.ok(order);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        // Call the service to delete the order
        orderService.deleteOrder(id);

        // Return HTTP status 204 (No Content) after deletion
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<String> createPayment(@PathVariable Long id, @RequestParam BigDecimal amount) {
        PaymentResponseDto paymentSuccessful = orderService.createPayment(id, amount);

        if (paymentSuccessful.isStatus()) {
            return ResponseEntity.status(HttpStatus.OK).body("Payment successful and order status updated to PAID.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed with message " + paymentSuccessful.getMessage());
        }
    }
}
