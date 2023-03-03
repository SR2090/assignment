package com.playapp.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.playapp.starter.data.User;
import com.playapp.starter.repository.UserRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * In the code above, we get full custom User object using UserRepository, then we build a UserDetails object using static build() method.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null) throw new UsernameNotFoundException(username  + " not found");
        return UserDetailsImplementation.build(user);
    }
	
}