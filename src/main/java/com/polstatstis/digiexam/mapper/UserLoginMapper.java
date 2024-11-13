package com.polstatstis.digiexam.mapper;
import com.polstatstis.digiexam.dto.UserLoginDTO;
import com.polstatstis.digiexam.entity.User;

public class UserLoginMapper {
    
        public static UserLoginDTO toDTO(User user) {
            return UserLoginDTO.builder()
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();
        }
    
        public static User toEntity(UserLoginDTO userDTO) {
            return User.builder()
                    .email(userDTO.getEmail())
                    .password(userDTO.getPassword())
                    .build();
        }
}
