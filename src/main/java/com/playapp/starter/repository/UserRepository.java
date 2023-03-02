package com.playapp.starter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.playapp.starter.data.User;

public interface UserRepository extends MongoRepository<User, String>{
    // Use the Spring data mongodb to store user details.
    User findByUserName(String userName);
}
