package com.playapp.starter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;

import com.playapp.starter.data.Event;
import com.playapp.starter.data.User;
import com.playapp.starter.data.UserRole;
import com.playapp.starter.repository.EventRepository;
import com.playapp.starter.repository.UserRepository;

@Service
public class EventService {
	
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private UserRepository userRepository;
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_EVENTPARTICIPATED')")
    public Optional<List<Event>> allUpcomingEventsSortedTime(){
        Optional<List<Event>> result = Optional.empty();
        Pageable sortedByCreatedTime = PageRequest.of(0, 100, Sort.by("createdAt").descending());
        try{
            result = Optional.of(eventRepository.findAll(sortedByCreatedTime).getContent());
        }catch(Exception e){
            return result;
        }
        return result;
    }

    /**
     * @param eventId 
     * This will handle then event detail page
     * List out all the event details 
     * Like all the users joining the event
     * @return
     */
    public Optional<Event> eventDetail(Integer eventId){
        Optional<Event> result = Optional.empty();
        try{
            result = eventRepository.findByIntegerId(eventId);
        }catch(Exception e){
            return result;
        }
        return result; 
    }

    /**
     * @param eventId 
     * eventId is used to uniquely identify the event
     * @param username
     * username is used to retrieve the user 
     * User is added as participant to the event
     * He is given a participant role that does not allow 
     * him to join other events
     * @return
     */
    public boolean userJoinsAnEvent(Integer eventId, String username){
        // Any user can request to join the game if the limit is available.
        Optional<Event> targetEvent = eventRepository.findByIntegerId(eventId);
        Optional<User> targetUser = Optional.ofNullable(userRepository.findByUsername(username));
        if(targetEvent.isEmpty() || targetUser.isEmpty()) return false;
        int eventAllowedUsers = targetEvent.get().getEventSize();
        targetUser.get().setRole(UserRole.ROLE_EVENTPARTICIPATED);
        if(targetEvent.get().getUsers().size() + 1 > eventAllowedUsers) return false;
        targetEvent.get().getUsers().add(targetUser.get());
        eventRepository.save(targetEvent.get());
        userRepository.save(targetUser.get());
        return false;
    }
}