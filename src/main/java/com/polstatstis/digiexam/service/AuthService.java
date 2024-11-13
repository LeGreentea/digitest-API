package com.polstatstis.digiexam.service;

import com.polstatstis.digiexam.dto.UserLoginDTO;
import com.polstatstis.digiexam.dto.UserRegistrationDTO;
import com.polstatstis.digiexam.dto.UserDTO;
import com.polstatstis.digiexam.entity.User;
import com.polstatstis.digiexam.exception.UserNotFoundException;
import com.polstatstis.digiexam.mapper.UserMapper;
import com.polstatstis.digiexam.repository.UserRepository;
import com.polstatstis.digiexam.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public UserDTO register(UserRegistrationDTO registrationDTO) {
        // Validasi email sudah terdaftar
        if (userRepository.existsByEmail(registrationDTO.getEmail().trim().toLowerCase())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .email(registrationDTO.getEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .name(registrationDTO.getName())
                .role(User.Role.USER)
                .build();

        User savedUser = userRepository.save(user);
        logger.info("User registered successfully: {}", savedUser.getEmail());
        return UserMapper.toDTO(savedUser);
    }

    public String login(UserLoginDTO loginDTO) {
        String email = loginDTO.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Login attempt failed: User not found with email: {}", email);
                    return new UserNotFoundException("User not found");
                });

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            logger.error("Login attempt failed: Invalid password for user: {}", email);
            throw new IllegalArgumentException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());
        logger.info("User logged in successfully: {}", email);
        return token;
    }
}