package com.polstatstis.digiexam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
