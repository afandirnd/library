package com.rnd4impact.identity.controller;

import com.rnd4impact.identity.dao.LoginRequest;
import com.rnd4impact.identity.dao.LoginResponse;
import com.rnd4impact.identity.dao.UserDao;
import com.rnd4impact.identity.entity.User;
import com.rnd4impact.identity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity createAdminUser(@RequestBody UserDao user) {
        try {
            User.Role.valueOf(user.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Unknown user role provided!");
        }

        User admin = userService.createUser(user);
        return ResponseEntity.ok(UserDao.mapUser(admin, false));
    }

    @PostMapping("/agent")
    public ResponseEntity createRentalAgentUser(@RequestBody UserDao user) {
        try {
            User.Role.valueOf(user.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Unknown user role provided!");
        }

        User admin = userService.createUser(user);
        return ResponseEntity.ok(UserDao.mapUser(admin, false));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }
}
