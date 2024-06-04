package com.example.onlineshop.controllers;

import com.example.onlineshop.dto.requests.AuthenticationRequest;
import com.example.onlineshop.dto.requests.RegisterRequest;
import com.example.onlineshop.dto.requests.UserRequestDTO;
import com.example.onlineshop.dto.responses.AuthenticationResponse;
import com.example.onlineshop.dto.responses.UserResponseDTO;
import com.example.onlineshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDto) {
        return ResponseEntity.ok(userService.createUser(userRequestDto));
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserRequestDTO userRequestDto) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDto));
    }

    @GetMapping("/all")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(userService.authenticate(request));
    }

}
