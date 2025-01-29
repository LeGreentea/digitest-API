package com.polstatstis.digiexam.service;

import com.polstatstis.digiexam.dto.UserDTO;
import com.polstatstis.digiexam.dto.UserLoginDTO;
import com.polstatstis.digiexam.dto.UserRegistrationDTO;
import com.polstatstis.digiexam.entity.User;
import com.polstatstis.digiexam.exception.UserNotFoundException;
import com.polstatstis.digiexam.mapper.UserMapper;
import com.polstatstis.digiexam.repository.UserRepository;
import com.polstatstis.digiexam.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    // Method to handle user registration
    public UserDTO register(UserRegistrationDTO registrationDTO) {
        // Validate if email is already registered
        if (userRepository.existsByEmail(registrationDTO.getEmail().trim().toLowerCase())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Build and save the new user
        User user = User.builder()
                .email(registrationDTO.getEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .name(registrationDTO.getName())
                .role(User.Role.USER)  // Set the default role to USER
                .build();

        User savedUser = userRepository.save(user);
        logger.info("User registered successfully: {}", savedUser.getEmail());
        return UserMapper.toDTO(savedUser);
    }

    // Method to handle user login
    public Map<String, Object> login(UserLoginDTO loginDTO) {
        String email = loginDTO.getEmail().trim().toLowerCase();

        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Login attempt failed: User not found with email: {}", email);
                    return new UserNotFoundException("User not found");
                });

        // Validate password
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            logger.error("Login attempt failed: Invalid password for user: {}", email);
            throw new IllegalArgumentException("Invalid password");
        }

        // Generate JWT token
        String token = jwtService.generateToken(user.getEmail());
        logger.info("User logged in successfully: {}", email);

        // Prepare response map
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("id", user.getId());
        return response;
    }
}