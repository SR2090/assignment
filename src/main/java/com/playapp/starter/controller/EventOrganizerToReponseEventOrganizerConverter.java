package com.playapp.starter.controller;

import java.util.List;

import org.modelmapper.AbstractConverter;

import com.playapp.starter.data.User;

public class EventOrganizerToReponseEventOrganizerConverter extends AbstractConverter<User, String> {

    @Override
    protected String convert(User targetUser) {
        return targetUser.getUserName();
    }
	
}