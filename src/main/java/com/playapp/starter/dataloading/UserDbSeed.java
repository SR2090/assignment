package com.playapp.starter.dataloading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.playapp.starter.data.Event;
import com.playapp.starter.data.EventStatus;
import com.playapp.starter.data.User;
import com.playapp.starter.repository.EventRepository;
import com.playapp.starter.repository.UserRepository;

@Component
public class UserDbSeed implements CommandLineRunner{
 
    private static final Logger loggerForCommandLine = LoggerFactory.getLogger(UserDbSeed.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventRepository eventRepository;

    @Override
    public void run(String... args) throws Exception {
        loggerForCommandLine.warn("INSIDE RUN METHIOD UN COMMAND LINE ARGUMENT");
        addUserDataToMongoDbForLoggingIn();
    }

    private void addUserDataToMongoDbForLoggingIn(){
        loggerForCommandLine.warn("INSIDE THE addUserDataToMongoDbForLoggingIn");
        try{
            if(userRepository.count() == 0){
                User mainUser = new User();
                PasswordEncoder passwordEncoderInUser = new BCryptPasswordEncoder();
                mainUser.setUserName("abc");
                mainUser.setPassword(passwordEncoderInUser.encode("123"));
                Long mainUserId = userRepository.count() + 1;
                mainUser.setUserId(mainUserId);
                userRepository.save(mainUser);
                if(eventRepository.count() == 0){
                    Event event1  = new Event();
                    List<User> users = new ArrayList<>();
                    users.add(mainUser);
                    event1.setEventName("Only event 1");
                    event1.setOrganizer(mainUser);
                    event1.setCreatedAt(Instant.now());
                    event1.setStatusOfEvent(EventStatus.COMMENCED);
                    event1.setUsers(users);
                    Long event1Id = eventRepository.count() + 1;
                    event1.setIntegerId(event1Id);
                    eventRepository.save(event1);
                    Event event2  = new Event();
                    event2.setEventName("Only event 2");
                    event2.setOrganizer(mainUser);
                    event2.setCreatedAt(Instant.now());
                    event2.setStatusOfEvent(EventStatus.COMMENCED);
                    event2.setUsers(users);
                    Long event2Id = eventRepository.count() + 1;
                    event1.setIntegerId(event2Id);
                    eventRepository.save(event2);
                }
                loggerForCommandLine.debug("After User Repo" + userRepository.count());
            }
        }catch(Exception e){
            loggerForCommandLine.debug("FAILED TO SEED MONGODB");
        }
        
    }
}