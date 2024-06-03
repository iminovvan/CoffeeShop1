package com.example.onlineshop.services.impl;

import com.example.onlineshop.config.security.JwtService;
import com.example.onlineshop.config.security.MyUserDetails;
import com.example.onlineshop.dto.requests.AuthenticationRequest;
import com.example.onlineshop.dto.requests.RegisterRequest;
import com.example.onlineshop.dto.responses.AuthenticationResponse;
import com.example.onlineshop.dto.responses.UserResponseDTO;
import com.example.onlineshop.dto.requests.UserRequestDTO;
import com.example.onlineshop.entities.User;
import com.example.onlineshop.enums.UserRole;
import com.example.onlineshop.exceptions.ResourceNotFoundException;
import com.example.onlineshop.repositories.UserRepository;
import com.example.onlineshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return mapToResponseDto(user);
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        user = userRepository.save(user);
        return mapToResponseDto(user);
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAge(request.getAge());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.ROLE_CUSTOMER);
        user = userRepository.save(user);
        //MyUserDetails is UserDetails implementation
        var jwtToken = jwtService.generateToken(new MyUserDetails(user));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        // at this point, the user is authenticated: username & password are correct
        // now we need to generate the token and send it back
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        // Jwt Service to generate the token
        // method using only User Details, without extra claims
        var jwtToken = jwtService.generateToken(new MyUserDetails(user));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public String deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return "User Deleted Successfully.";
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        user = userRepository.save(user);
        return mapToResponseDto(user);
    }

    //using userRepository get all users from the database
    //convert them to userResponseDTO before returning
    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(this::mapToResponseDto).collect(Collectors.toList());
    }

    public UserResponseDTO mapToResponseDto(User user){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}


/*
* userList.stream() --> converts List<User> into a Stream of User objects,
* which provides a sequence of elements for processing
*
* map(UserResponseDTO::new) --> a constructor reference, shorthand notation
* for calling the constructor of UserResponseDTO for each element of the stream
*
* collect(Collectors.toList()) --> gathers all transformed objects into a new List
* */