package com.polstatstis.digiexam.controller;

import com.polstatstis.digiexam.dto.UserLoginDTO;
import com.polstatstis.digiexam.dto.UserRegistrationDTO;
import com.polstatstis.digiexam.dto.UserDTO;
import com.polstatstis.digiexam.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "registrasi pengguna.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))}), @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)})

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRegistrationDTO registrationDTO) {
        UserDTO user = authService.register(registrationDTO);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "User login untuk dapat access token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email and access token", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))}), @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)})

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        return ResponseEntity.ok(token);
    }
}
