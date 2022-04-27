package com.example.demo.service;

import com.example.demo.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    Users getUserByEmail(String email);
    Users createUser(Users users);
    List<Users> getAllUsers(Long id);
    Users save(Users user);
}
