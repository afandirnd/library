package com.rnd4impact.authenticationservice.controller;

import com.rnd4impact.authenticationservice.dao.AuthRequest;
import com.rnd4impact.authenticationservice.dao.AuthResponse;
import com.rnd4impact.authenticationservice.dao.UserDao;
import com.rnd4impact.authenticationservice.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/createAdmin")
    public ResponseEntity<AuthResponse> createAdmin(@RequestBody UserDao request, @RequestHeader("x-role") String role) {
        return ResponseEntity.ok(authService.createUser(request, role));
    }

    @PostMapping(value = "/createRentalAgent")
    public ResponseEntity<AuthResponse> createRentalAgent(@RequestBody UserDao request, @RequestHeader("x-role") String role) {
        return ResponseEntity.ok(authService.createUser(request, role));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
