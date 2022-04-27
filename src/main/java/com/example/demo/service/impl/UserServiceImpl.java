package com.example.demo.service.impl;

import com.example.demo.entity.Roles;
import com.example.demo.entity.Users;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users myUser = userRepository.findByMail(s);
        if (myUser!=null){

            User secUser = new User(myUser.getMail(), myUser.getPassword(), myUser.getRoles());
            return secUser;

        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByMail(email);
    }

    @Override
    public Users createUser(Users users) {
        Users checkUser = userRepository.findByMail(users.getMail());
        if (checkUser==null){
            Roles role = roleRepository.findByRole("ROLE_USER");
            if (role!=null){
                ArrayList<Roles> roles = new ArrayList<>();
                roles.add(role);
                users.setRoles(roles);
                users.setPassword(passwordEncoder.encode(users.getPassword()));
                return userRepository.save(users);

            }
        }
        return null;
    }

    @Override
    public List<Users> getAllUsers(Long id) {
        return null;
    }

    @Override
    public Users save(Users user) {
        return null;
    }
}
