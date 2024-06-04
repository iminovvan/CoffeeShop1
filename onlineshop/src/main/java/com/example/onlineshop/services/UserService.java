package com.example.onlineshop.services;

import com.example.onlineshop.dto.requests.AuthenticationRequest;
import com.example.onlineshop.dto.requests.RegisterRequest;
import com.example.onlineshop.dto.responses.AuthenticationResponse;
import com.example.onlineshop.dto.responses.UserResponseDTO;
import com.example.onlineshop.dto.requests.UserRequestDTO;

import java.util.List;

public interface UserService {
    public UserResponseDTO getUserById(Long userId);
    public UserResponseDTO createUser(UserRequestDTO userDto);

    public AuthenticationResponse register(RegisterRequest request);

    public AuthenticationResponse authenticate(AuthenticationRequest request);

    public String deleteUser(Long userId);
    public UserResponseDTO updateUser(Long id, UserRequestDTO userDto);
    public List<UserResponseDTO> getAllUsers();
}
