package com.example.onlineshop.dto;

import com.example.onlineshop.dto.responses.UserResponseDTO;
import com.example.onlineshop.enums.OrderStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private OrderStatus status;
    private UserResponseDTO user;

    private List<OrderItemDto> items;
}
