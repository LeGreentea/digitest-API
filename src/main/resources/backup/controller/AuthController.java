package com.polstatstis.digiexam.controller;

import com.polstatstis.digiexam.dto.LoginDTO;
import com.polstatstis.digiexam.dto.UserDTO;
import com.polstatstis.digiexam.models.User;
import com.polstatstis.digiexam.services.AuthService;
import com.polstatstis.digiexam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.authenticateUser(loginDTO);
        return ResponseEntity.ok("Bearer " + token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = authService.registerUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        authService.changePassword(username, oldPassword, newPassword);
        return ResponseEntity.ok("Password changed successfully");
    }
}
