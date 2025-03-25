package com.jwt.identityservice.controller;

import com.jwt.identityservice.UserNotFoundException;
import com.jwt.identityservice.dto.AuthRequest;
import com.jwt.identityservice.entity.UserCredential;
import com.jwt.identityservice.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private UserAuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
       return service.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest request) throws UserNotFoundException {
        Authentication authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authenticate.isAuthenticated()) {
            return service.generateToken(request.getUsername());
        }
        else {
            throw new UserNotFoundException("User is not registered");
        }
    }

    @GetMapping("/validate")
    public  String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
