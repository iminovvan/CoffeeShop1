package com.example.onlineshop.dto;

import com.example.onlineshop.entities.OrderItem;
import com.example.onlineshop.entities.Product;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private int quantity;
    private double price;
    private ProductDto product;

    private Long orderId;
    private double totalPrice;
}
