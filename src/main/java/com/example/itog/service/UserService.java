package com.example.itog.service;

import com.example.itog.repositories.UserRepository;
import com.example.itog.model.AppUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUsers registerUser(String name, String email, String password) {
        AppUsers user = new AppUsers();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        return userRepository.save(user);
    }

    public Optional<AppUsers> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
