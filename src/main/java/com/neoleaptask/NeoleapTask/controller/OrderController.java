package com.neoleaptask.NeoleapTask.controller;

import com.neoleaptask.NeoleapTask.dto.OrderRequestDto;
import com.neoleaptask.NeoleapTask.dto.PaymentResponseDto;
import com.neoleaptask.NeoleapTask.model.Order;
import com.neoleaptask.NeoleapTask.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Order Operations", description = "Operations for creating, retrieving, updating, and deleting orders, as well as processing payments.")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new order",
            description = "This endpoint creates a new order based on the provided order details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created successfully",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Order> createOrder(@RequestBody @Validated OrderRequestDto orderDto) {
        Order createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get order by ID",
            description = "Fetches an order using the order's unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order retrieved successfully",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    public ResponseEntity<Order> getOrderById(@Parameter(description = "ID of the order to be fetched")
                                              @PathVariable Long id) {
        logger.info("Fetching order with ID {}", id);
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    @Operation(
            summary = "Get all orders",
            description = "Fetches all orders from the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of orders",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "204", description = "No orders found")
            }
    )
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing order",
            description = "Updates an order with the provided details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order updated successfully",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody @Validated Order updatedOrder) {
        Order order = orderService.updateOrder(id, updatedOrder);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an order",
            description = "Deletes an order from the system using the provided order ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/payment")
    @Operation(
            summary = "Process payment for an order",
            description = "Processes a payment for the specified order ID and updates the order status accordingly.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payment successful, order status updated"),
                    @ApiResponse(responseCode = "400", description = "Payment failed")
            }
    )
    public ResponseEntity<String> createPayment(@PathVariable Long id,
                                                @RequestParam BigDecimal amount) {
        PaymentResponseDto paymentSuccessful = orderService.createPayment(id, amount);
        if (paymentSuccessful.isStatus()) {
            return ResponseEntity.status(HttpStatus.OK).body("Payment successful and order status updated to PAID.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed with message " + paymentSuccessful.getMessage());
        }
    }
}
