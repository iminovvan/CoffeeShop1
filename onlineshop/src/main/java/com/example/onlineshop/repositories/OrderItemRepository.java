package com.example.onlineshop.repositories;

import com.example.onlineshop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
