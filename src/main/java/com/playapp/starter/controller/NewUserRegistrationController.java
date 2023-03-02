package com.playapp.starter.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playapp.starter.repository.UserRepository;
import com.playapp.starter.service.UserDetailService;
import com.playapp.starter.data.User;

@RestController
@RequestMapping("/v1/new-user-registration")
public class NewUserRegistrationController {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    public NewUserRegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }  
    @Bean
    public UserDetailService userDetailService(UserRepository userRepo){
        return username -> {
            User user = userRepo.findByUserName(username);
            if (user != null) return user;

            throw new UsernameNotFoundException("User " + username + " not found");
        };
    }
    @PostMapping(consumes="application/json")
    public ResponseEntity<String> registerNewUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return null;
    } 
}
