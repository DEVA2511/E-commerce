package com.ecommers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    private final PasswordEncoder passwordEncoder1;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder1 = (BCryptPasswordEncoder) passwordEncoder;
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder1.encode(rawPassword);
    }
}