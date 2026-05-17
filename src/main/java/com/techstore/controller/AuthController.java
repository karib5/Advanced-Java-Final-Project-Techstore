package com.techstore.controller;

import com.techstore.dto.request.LoginRequest;
import com.techstore.dto.request.RegisterRequest;
import com.techstore.dto.response.ApiResponse;
import com.techstore.dto.response.AuthResponse;
import com.techstore.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Registered successfully", response));
    }

    @PostMapping("/register-admin")
    public ResponseEntity<ApiResponse<AuthResponse>> registerAdmin(
            @Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.registerAdmin(request);
        return ResponseEntity.ok(ApiResponse.success("Admin registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }
}
