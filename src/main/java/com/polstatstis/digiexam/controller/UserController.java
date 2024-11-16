package com.polstatstis.digiexam.controller;

import com.polstatstis.digiexam.dto.ChangePasswordDTO;
import com.polstatstis.digiexam.dto.UserDTO;
import com.polstatstis.digiexam.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Mendapatkan profil pengguna")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the user",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content) })

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Long id) {
        UserDTO user = userService.getUserProfile(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update profil pengguna")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User profile updated",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content) })

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserProfile(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUserProfile(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Mengganti password oleh pengguna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid old password",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })

    @PreAuthorize("#id == authentication.principal.id")
    @PutMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(id, changePasswordDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete user oleh admin")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content) })

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mengubah role pengguna oleh admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role changed successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/role")
    public ResponseEntity<UserDTO> changeUserRole(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.changeUserRole(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
