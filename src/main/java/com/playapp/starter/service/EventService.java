package com.playapp.starter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import com.playapp.starter.data.Event;
import com.playapp.starter.repository.EventRepository;

@Service
public class EventService {
	
    @Autowired
    private EventRepository eventRepository;

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
     * eventId is the autogenerate integer mongo id that uniquely 
     * identifies the event.
     * Is used to update the event by adding details of the user
     * in the participants list when the user joins the event
     * @return
     */
    public boolean updateEvent(Integer eventId){
        return false;
    }
}