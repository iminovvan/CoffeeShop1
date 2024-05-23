package com.example.onlineshop.services.impl;

import com.example.onlineshop.dto.OrderDto;
import com.example.onlineshop.dto.OrderItemDto;
import com.example.onlineshop.dto.ProductDto;
import com.example.onlineshop.dto.responses.UserResponseDTO;
import com.example.onlineshop.entities.Order;
import com.example.onlineshop.entities.OrderItem;
import com.example.onlineshop.entities.Product;
import com.example.onlineshop.entities.User;
import com.example.onlineshop.exceptions.ResourceNotFoundException;
import com.example.onlineshop.repositories.OrderRepository;
import com.example.onlineshop.repositories.ProductRepository;
import com.example.onlineshop.repositories.UserRepository;
import com.example.onlineshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found."));
        return mapToDto(order);
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setStatus(orderDto.getStatus());
        User user = userRepository.findById(orderDto.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        order.setUser(user);

        List<OrderItem> orderItems = orderDto.getItems().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());

            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());
        order.setItems(orderItems);
        orderRepository.save(order);
        return mapToDto(order);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found."));
        order.setStatus(orderDto.getStatus());
        User user = userRepository.findById(orderDto.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        order.setUser(user);
        //have included Order Item creation in Order creation
        //can create them at the same time,
        //when testing using Postman OrderCreate
        List<OrderItem> orderItems = orderDto.getItems().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());

            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());
        order.setItems(orderItems);
        orderRepository.save(order);
        return mapToDto(order);
    }

    @Override
    public String deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
        return "Order Deleted Successfully.";
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public OrderDto addOrderItem(Long id, OrderItemDto orderItemDto){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setQuantity(orderItemDto.getQuantity());
        Product product = productRepository.findById(orderItemDto.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        orderItem.setProduct(product);
        orderItem.setOrder(order);

        order.getItems().add(orderItem);
        order = orderRepository.save(order);

        return mapToDto(order);
    }
    private OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setStatus(order.getStatus());
        orderDto.setUser(mapToUserDto(order.getUser()));
        orderDto.setItems(order.getItems().stream()
                .map(this::mapToOrderItemDto)
                .collect(Collectors.toList()));
        return orderDto;
    }

    private UserResponseDTO mapToUserDto(User user) {
        UserResponseDTO userDto = new UserResponseDTO();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }

    private OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setProduct(mapToProductDto(orderItem.getProduct()));
        orderItemDto.setOrderId(orderItem.getOrder().getId());
        orderItemDto.setTotalPrice(orderItem.getTotalItemPrice());
        return orderItemDto;
    }

    private ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
