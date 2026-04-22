package com.example.jwtdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "🛡️ Admin", description = "Admin-only endpoints for testing access control")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Admin dashboard", description = "Returns admin info. Only accessible by ROLE_ADMIN.")
    public ResponseEntity<Map<String, Object>> dashboard(Authentication authentication) {
        return ResponseEntity.ok(Map.of(
                "message", "Welcome to the Admin Dashboard!",
                "loggedInAs", authentication.getName(),
                "authorities", authentication.getAuthorities().toString(),
                "serverTime", LocalDateTime.now().toString()
        ));
    }

    @GetMapping("/ping")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Admin ping", description = "Simple health check for admin access.")
    public ResponseEntity<Map<String, String>> ping() {
        return ResponseEntity.ok(Map.of(
                "status", "OK",
                "role", "ADMIN",
                "message", "You have ADMIN access!"
        ));
    }
}
