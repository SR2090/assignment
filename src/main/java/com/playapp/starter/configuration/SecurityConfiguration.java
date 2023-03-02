package com.playapp.starter.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.playapp.starter.service.UserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    // @Autowired
    // private UserDetailService userDetailService;

    // public SecurityConfiguration(UserDetailService userDetailService){
    //     this.userDetailService = userDetailService;
    // }

    // @Bean
    // public AuthenticationManager customAuthenticationManager(HttpSecurity http) throws Exception {
    //     AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject
    //       (AuthenticationManagerBuilder.class);
    //     authenticationManagerBuilder.userDetailsService(userDetailService)
    //         .passwordEncoder(passwordEncoder());
    //     return authenticationManagerBuilder.build();
    // }

    /**
     * The main thing it does is declare a PasswordEncoder bean, which we’ll use both 
     * when creating new users and when authenticating users at login
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure security rules
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .and()
        .build();
    }
}