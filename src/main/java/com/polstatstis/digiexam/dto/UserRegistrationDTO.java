package com.polstatstis.digiexam.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDTO {

    private String email;
    private String password;
    private String name;
}
