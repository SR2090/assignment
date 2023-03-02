package com.playapp.starter.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;

import com.playapp.starter.data.User;
// move to converter
/**
 * Class is used by model mapper to convert the list of users present inside event
 * into list of string of usernames obtained from the list of users.
 * This list tells us all the users that are associated with the event.
 */
public class ListOfUsersInEventToResponseEventDTO extends AbstractConverter<List<User>, List<String>>{

    @Override
    protected List<String> convert(List<User> sourceListOfUsers) {
        // TODO Auto-generated method stub
       return sourceListOfUsers
       .stream()
       .map(User::getUserName)
       .collect(Collectors.toList());
    }
	
}