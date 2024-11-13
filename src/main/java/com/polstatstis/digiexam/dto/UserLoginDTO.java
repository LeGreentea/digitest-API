package com.polstatstis.digiexam.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDTO {

    private String email;
    private String password;
}
