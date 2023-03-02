package com.playapp.starter.repository;

import org.springframework.data.repository.CrudRepository;

import com.playapp.starter.data.User;

public interface UserRepository extends CrudRepository<User, String>{
    // Use the Spring data mongodb to store user details.
    User findByUserName(String userName);
}
