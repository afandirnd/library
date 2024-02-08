package com.rnd4impact.authenticationservice.service;

import com.rnd4impact.authenticationservice.dao.AuthRequest;
import com.rnd4impact.authenticationservice.dao.AuthResponse;
import com.rnd4impact.authenticationservice.dao.LoginResponse;
import com.rnd4impact.authenticationservice.dao.UserDao;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthResponse createUser(UserDao request, String role) {
        if (role == null || !role.equalsIgnoreCase("role_admin")) {
            return new AuthResponse(null, null, "Only admins can creat users");
        }

        UserDao newUser;
        if (request.getRole().equalsIgnoreCase("role_admin")) {
            newUser = restTemplate.postForObject("http://IDENTITY/api/v1/users", request, UserDao.class);
        }
        else if (request.getRole().equalsIgnoreCase("role_rental_agent")) {
            newUser = restTemplate.postForObject("http://IDENTITY/api/v1/users/agent", request, UserDao.class);
        }
        else {
            return new AuthResponse(null, null, "Unknown role");
        }

        String accessToken = jwtUtil.generate(newUser.getUuid(), newUser.getRole(), "ACCESS");
        String refreshToken = jwtUtil.generate(newUser.getUuid(), newUser.getRole(), "REFRESH");

        return new AuthResponse(accessToken, refreshToken, null);
    }

    public AuthResponse login(AuthRequest request) {
        LoginResponse response = restTemplate.postForObject(
            "http://IDENTITY/api/v1/users/login", request, LoginResponse.class
        );

        if (response == null) {
            return new AuthResponse(null, null, "Unknown error");
        }

        if (response.getError() == null) {
            String accessToken = jwtUtil.generate(response.getUser().getUuid(), response.getUser().getRole(), "ACCESS");
            String refreshToken = jwtUtil.generate(response.getUser().getUuid(), response.getUser().getRole(), "REFRESH");

            return new AuthResponse(accessToken, refreshToken, null);
        }

        return new AuthResponse(null, null, response.getError());
    }
}
