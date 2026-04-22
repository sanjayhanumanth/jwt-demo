package com.example.jwtdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "User registration data")
public class RegisterRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(description = "Username (3-50 chars)", example = "john_doe")
    private String username;

    @NotBlank
    @Email
    @Schema(description = "Valid email address", example = "john@example.com")
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    @Schema(description = "Password (min 6 chars)", example = "secret123")
    private String password;

    @Schema(description = "Roles: ROLE_USER or ROLE_ADMIN", example = "[\"ROLE_USER\"]")
    private Set<String> roles;
}
