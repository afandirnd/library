package com.rnd4impact.identity.dao;

import com.rnd4impact.identity.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDao {

    private String uuid;
    private String email;
    private String password;
    private String role;

    public static UserDao mapUser(User user, boolean includePassword) {
        return new UserDao(
            user.getUuid(),
            user.getEmail(),
            includePassword ? user.getPassword() : null,
            user.getRole().name()
        );
    }
}
