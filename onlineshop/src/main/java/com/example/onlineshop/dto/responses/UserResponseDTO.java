package com.example.onlineshop.dto.responses;

import com.example.onlineshop.entities.User;
import com.example.onlineshop.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private UserRole role;
}
