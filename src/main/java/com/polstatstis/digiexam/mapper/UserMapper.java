package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.UserDTO;
import com.polstatstis.digiexam.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .build();
    }

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .role(User.Role.valueOf(userDTO.getRole()))
                .build();
    }
}
