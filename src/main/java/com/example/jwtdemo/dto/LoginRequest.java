package com.example.jwtdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login credentials")
public class LoginRequest {

    @NotBlank
    @Schema(description = "Username", example = "admin")
    private String username;

    @NotBlank
    @Schema(description = "Password", example = "password123")
    private String password;
}
