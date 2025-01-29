package com.polstatstis.digiexam.service;

import com.polstatstis.digiexam.dto.ChangePasswordDTO;
import com.polstatstis.digiexam.dto.UserDTO;
import com.polstatstis.digiexam.entity.User;
import com.polstatstis.digiexam.exception.UserNotFoundException;
import com.polstatstis.digiexam.mapper.UserMapper;
import com.polstatstis.digiexam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    public UserDTO getUserProfile(Long userId) {
        logger.info("Fetching user profile for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        logger.info("Found user: {}", user);
        UserDTO dto = UserMapper.toDTO(user);
        return dto;
    }

    @Transactional
    public UserDTO updateUserProfile(Long userId, UserDTO userDTO) {
        logger.info("Updating user profile for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(User.Role.valueOf(userDTO.getRole()));
        userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    @Transactional
    public UserDTO changeUserRole(Long id, UserDTO userDTO) {
        logger.info("Changing user role for user ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User.Role newRole;
        try {
            newRole = User.Role.valueOf(userDTO.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid role provided: {}", userDTO.getRole(), e);
            throw new RuntimeException("Invalid role: " + userDTO.getRole());
        }

        user.setRole(newRole);
        userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordDTO changePasswordDTO) {
        logger.info("Changing password for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            logger.error("Invalid old password provided for user ID: {}", userId);
            throw new IllegalArgumentException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        logger.info("Deleting user with ID: {}", userId);
        if (!userRepository.existsById(userId)) {
            logger.error("User not found with ID: {}", userId);
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }
}