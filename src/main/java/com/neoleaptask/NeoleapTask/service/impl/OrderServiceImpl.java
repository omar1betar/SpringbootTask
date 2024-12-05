package com.neoleaptask.NeoleapTask.service.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.neoleaptask.NeoleapTask.dto.OrderRequestDto;
import com.neoleaptask.NeoleapTask.mapper.OrderMapper;
import com.neoleaptask.NeoleapTask.model.Order;
import com.neoleaptask.NeoleapTask.repository.OrderRepository;
import com.neoleaptask.NeoleapTask.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final HazelcastInstance hazelcastInstance;
    private final IMap<Long, Order> ordersMap;

    public OrderServiceImpl(OrderRepository orderRepository, HazelcastInstance hazelcastInstance) {
        this.orderRepository = orderRepository;
        this.hazelcastInstance = hazelcastInstance;
        this.ordersMap = hazelcastInstance.getMap("orders");
    }

    /**
     * Retrieves an order by its ID. It will cache the order individually in Hazelcast.
     * @param id The ID of the order to retrieve.
     * @return The retrieved order.
     */
    @Override
    @Transactional
    @CachePut(value = "orders", key = "#id") // Cache the updated order by its ID.
    @CacheEvict(value = "orders", key = "'allOrders'") // Evict the cached list of all orders.
    public Order getOrderById(Long id) {
        // First, try to get the order from Hazelcast
        Order order = ordersMap.get(id);
        System.out.println("getorderbyid start");
        System.out.println(order);
        System.out.println("getorderbyid end");

        if (order == null) {
            // If the order is not found in the cache, retrieve it from the DB
            order = orderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
            // Store the order in the cache
            ordersMap.put(id, order);
        }

        return order;
    }

    /**
     * Retrieves all orders. If the orders are not cached, it fetches from the database
     * and caches each order individually.
     * @return A list of all orders.
     */
    @Override
    public List<Order> getAllOrders() {
        // Check if the orders are already in the cache
        List<Order> orders = new ArrayList<>();
        for (Long id : ordersMap.keySet()) {
            orders.add(ordersMap.get(id));
        }

        if (orders.isEmpty()) {
            // Cache miss, fetch orders from DB and cache each one
            orders = orderRepository.findAll();
            orders.forEach(order -> ordersMap.put(order.getId(), order)); // Store each order individually in the cache
        }

        return orders;
    }

    /**
     * Creates a new order. This method will evict the cached list of orders to ensure
     * the next retrieval gets the updated list.
     * @param orderDto The order data transfer object to create a new order.
     * @return The created order.
     */
    @Override
    @Transactional
    @CacheEvict(value = "orders", key = "'allOrders'") // Evict the entire list cache
    public Order createOrder(OrderRequestDto orderDto) {
        Order orderEntity = OrderMapper.toOrder(orderDto);
        Order savedOrder = orderRepository.save(orderEntity);
        // Cache the new order individually
        ordersMap.put(savedOrder.getId(), savedOrder);
        return savedOrder;
    }

    /**
     * Updates an existing order. It updates the cache for the individual order and evicts
     * the cached list of all orders.
     * @param id The ID of the order to update.
     * @param updatedOrder The updated order details.
     * @return The updated order.
     */
    @Override
    @Transactional
    @CachePut(value = "orders", key = "#id") // Put updated order into cache
    @CacheEvict(value = "orders", key = "'allOrders'") // Evict the cached list of all orders
    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = getOrderById(id);
        existingOrder.setDescription(updatedOrder.getDescription());
        existingOrder.setAmount(updatedOrder.getAmount());
        existingOrder.setStatus(updatedOrder.getStatus());
        Order savedOrder = orderRepository.save(existingOrder);

        // Cache the updated order individually
        ordersMap.put(savedOrder.getId(), savedOrder);
        return savedOrder;
    }


    /**
     * Deletes an order by its ID. This will evict the order from the cache, ensuring
     * that the cache is consistent with the database.
     * @param id The ID of the order to delete.
     */
    @Override
    @Transactional
    @CacheEvict(value = "orders", key = "#id") // Evict the order from the cache.
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
        // Evict the order from Hazelcast as well to maintain cache consistency
        ordersMap.remove(id);
    }
}
