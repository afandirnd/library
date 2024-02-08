package com.rnd4impact.identity.service;

import com.rnd4impact.identity.dao.LoginRequest;
import com.rnd4impact.identity.dao.LoginResponse;
import com.rnd4impact.identity.dao.UserDao;
import com.rnd4impact.identity.entity.User;
import com.rnd4impact.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User createUser(UserDao user) {
        User created = new User();
        created.setEmail(user.getEmail());
        created.setRole(User.Role.valueOf(user.getRole().toUpperCase()));
        created.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(created);
    }

    public LoginResponse authenticate(LoginRequest request) {
        LoginResponse response = new LoginResponse();
        User user = userRepository.findUserByEmail(request.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.setError("Email or password are incorrect!");
            return response;
        }

        response.setUser(UserDao.mapUser(user, false));
        return response;
    }
}
