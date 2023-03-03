package com.playapp.starter.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playapp.starter.data.User;
import com.playapp.starter.data.UserRole;
import com.playapp.starter.exchange.JwtResponse;
import com.playapp.starter.exchange.LoginRequest;
import com.playapp.starter.exchange.RequestUserDetails;
import com.playapp.starter.jwtauth.JwtUtils;
import com.playapp.starter.repository.UserRepository;
import com.playapp.starter.service.UserDetailsImplementation;
@CrossOrigin(origins="*", maxAge = 3600)
@RestController
@RequestMapping("/v1/new-user-registration")
public class NewUserRegistrationController {
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/")
    public String methodJustForTesting(){
        return "ASDASD";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();		
		String userRole = UserRole.ROLE_ADMIN.name();

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 userRole));
    }
    
    @PostMapping("/signup")
    public ResponseEntity<String> registerNewUser(@Valid @RequestBody RequestUserDetails user){
        // Optional.ofNullable prevents null pointer exception
        Optional<User> userFromBodyByUsername = Optional.ofNullable(userRepo.findByUsername(user.getUsername()));
        Optional<User> userFromBodyByEmail = Optional.ofNullable(userRepo.findByEmail(user.getEmail()));
        if(userFromBodyByUsername.isPresent()){
            return new ResponseEntity<String>(null).badRequest().body("User name "+user.getUsername() + "  already exist" );
        }
        if(userFromBodyByEmail.isPresent()){
            return new ResponseEntity<String>(null).badRequest().body("User with "+user.getEmail() + "  already dealt" );
        }
        Long Id = userRepo.count();
        User newUser = new User(Id + 1, user.getEmail(), user.getUsername(), passwordEncoder.encode(user.getPassword()), UserRole.ROLE_USER);
        userRepo.save(newUser);

        return ResponseEntity.ok("User registered sucessfull");
    } 
}
