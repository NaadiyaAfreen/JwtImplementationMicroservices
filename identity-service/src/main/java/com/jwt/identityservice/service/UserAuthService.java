package com.jwt.identityservice.service;

import com.jwt.identityservice.entity.UserCredential;
import com.jwt.identityservice.jwt.JwtUtil;
import com.jwt.identityservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;;

    public String saveUser(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        repository.save(userCredential);
        return "User is added to the system";
    }

    public String generateToken(String userName) {
        return jwtUtil.generateToken(userName);
    }

    public void validateToken(String token) {
        jwtUtil.validateToken(token);
    }
}
