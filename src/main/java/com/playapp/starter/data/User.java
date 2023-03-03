package com.playapp.starter.data;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "User")
public class User{
    @MongoId
    private String Id;
    private Long userId;
    private String email;
    private String username;
    private String password;
    private UserRole role;
    
    public User() {}

    public User(Long userId, String email, String username, String password, UserRole role){
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public UserRole getRole(){
        return role;
    }

    public Long getuserId(){
        return userId;
    }

    public String getPassword(){
        return password;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public void setRole(UserRole roleOfUser){
        this.role = roleOfUser;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setUserName(String userName){
        this.username = userName;
    }
}
