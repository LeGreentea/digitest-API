package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.UserDTO;
import com.polstatstis.digiexam.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().name());
        return userDTO;
    }

    public User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        // Assuming roles are handled in some way (e.g., with an enum)
        user.setRole(User.Role.valueOf(userDTO.getRole()));
        return user;
    }
}
