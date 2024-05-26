package com.example.onlineshop.dto.requests;

import com.example.onlineshop.enums.UserRole;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;
    private UserRole role;
}
