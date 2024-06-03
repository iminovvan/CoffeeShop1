package com.example.onlineshop.services;

import com.example.onlineshop.dto.OrderDto;
import com.example.onlineshop.dto.OrderItemDto;
import com.example.onlineshop.entities.Order;

import java.util.List;

public interface OrderService {
    public OrderDto getOrderById(Long orderId);
    public OrderDto createOrder(OrderDto orderDto);
    public OrderDto updateOrder(Long id, OrderDto orderDto);
    public String deleteOrderById(Long orderId);
    public List<OrderDto> getAllOrders();
    public OrderDto addOrderItem(Long id, OrderItemDto orderItemDto);
}
