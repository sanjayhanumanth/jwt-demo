package com.example.jwtdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "JWT token response after successful login")
public class JwtResponse {

    @Schema(description = "JWT access token")
    private String token;

    @Builder.Default
    @Schema(description = "Token type", example = "Bearer")
    private String type = "Bearer";

    @Schema(description = "User ID")
    private Long id;

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "User roles")
    private Set<String> roles;
}
