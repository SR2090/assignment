package com.playapp.starter.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// we need UserDetailsService for getting UserDetails object.
public interface UserDetailService {
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}