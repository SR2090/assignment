package com.playapp.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.playapp.starter.data.User;
import com.playapp.starter.repository.UserRepository;

@Service
public class UserAuthenticationService implements UserDetailService{

    @Autowired
    private UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null) throw new UsernameNotFoundException(username + " not found in db");
        return user;
    }
	
}