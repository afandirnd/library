package com.rnd4impact.authenticationservice.dao;

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
}
