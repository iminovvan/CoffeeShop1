package com.example.onlineshop;

import com.example.onlineshop.dto.OrderDto;
import com.example.onlineshop.dto.ProductDto;
import com.example.onlineshop.dto.responses.UserResponseDTO;
import com.example.onlineshop.entities.Order;
import com.example.onlineshop.entities.User;
import com.example.onlineshop.enums.OrderStatus;
import com.example.onlineshop.enums.UserRole;
import com.example.onlineshop.repositories.OrderRepository;
import com.example.onlineshop.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private User adminUser;

    //@BeforeAll
    public void setUp() {
        adminUser = new User();
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setAge(30);
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword(passwordEncoder.encode("password"));
        adminUser.setRole(UserRole.ROLE_ADMIN);
        userRepository.save(adminUser);
    }


    @Test
    public void testCreateOrder() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setStatus(OrderStatus.WAITING);
        orderDto.setUser(new UserResponseDTO(adminUser.getId(), adminUser.getFirstName(), adminUser.getLastName(), adminUser.getAge(), adminUser.getEmail(), adminUser.getRole()));
        orderDto.setItems(Collections.emptyList());

        String json = objectMapper.writeValueAsString(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(httpBasic("admin@example.com", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("WAITING"));
    }

    @Test
    public void testGetOrderById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", 1)
                        .with(httpBasic("admin@example.com", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(OrderStatus.WAITING));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllOrders() throws Exception {
        mockMvc.perform(get("/orders/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
