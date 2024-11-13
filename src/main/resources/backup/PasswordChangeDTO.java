package com.polstatstis.digiexam.dto;

import lombok.Data;

@Data
public class PasswordChangeDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
