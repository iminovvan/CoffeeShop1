package com.example.onlineshop;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.onlineshop.dto.requests.UserRequestDTO;
import com.example.onlineshop.enums.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testCreateUser() throws Exception {

        UserRequestDTO userRequestDTO = new UserRequestDTO("John", "Doe", 30, "john.doe@gmail.com", "john", UserRole.ROLE_CUSTOMER);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.doe@gmail.com"));
        }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetUserById() throws Exception {

        mockMvc.perform(get("/users/{id}", 13))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(13));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateUser() throws Exception {

        UserRequestDTO userRequestDTO = new UserRequestDTO("Alan", "Jones", 26, "alan.jones@gmail.com", "alan", UserRole.ROLE_CUSTOMER);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk());

        // Update the user
        userRequestDTO.setFirstName("Alan Updated");
        userRequestDTO.setLastName("Jones Updated");


        mockMvc.perform(put("/users/{id}", 18)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alan Updated"))
                .andExpect(jsonPath("$.lastName").value("Jones Updated"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 19))
                .andExpect(status().isNoContent());
    }

}
