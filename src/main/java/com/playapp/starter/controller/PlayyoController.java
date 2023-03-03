package com.playapp.starter.controller;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playapp.starter.data.Event;
import com.playapp.starter.exchange.RequestJoinEvent;
import com.playapp.starter.service.EventService;

// conversion of reposiotry response to api response
// DTO object
@RestController
@RequestMapping("/v1")
public class PlayyoController {

    private static final Logger loggerForPlayyoController = LoggerFactory.getLogger(PlayyoController.class);

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String sayHello() {
        loggerForPlayyoController.warn("SENDING RESPONSE sayHello");
        return "Public Accessible Path";
    }

    @GetMapping("/events")
    @PreAuthorize("hasRole('USER') OR hasRole('EVENTPARTICIPATED')")
    public ResponseEntity<String> getAllEvents(){
        
        Optional<List<Event>> result = Optional.empty();
        try{
            loggerForPlayyoController.warn("Inside the /events method");
            result = eventService.allUpcomingEventsSortedTime();
            if(result.isEmpty()) return ResponseEntity.noContent().build();
            loggerForPlayyoController.warn(" getAllEvents " + result.get().size());
        }catch(NullPointerException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        StringBuilder sb = new StringBuilder();
        for(Event e : result.get()){
            sb.append(e.getEventName());
            sb.append(e.getCreatedAt().atZone(ZoneOffset.UTC).toLocalTime());
            sb.append("+");
        }
        // return new ResponseEntity<>(convertEventsToResponseEventDtos(result.get()), HttpStatus.OK);
        return ResponseEntity.ok(sb.toString());
    }

    // This handles the details about individual event
	@GetMapping("/event-details/{id}")
    @PreAuthorize("hasRole('USER') OR hasRole('EVENTPARTICIPATED')")
    public ResponseEntity<String> getEntity(@PathVariable("id") String Id){
        
        Optional<Event> result = Optional.empty();
        try{
            loggerForPlayyoController.warn("Inside the /event-details/{id} method");
            result = eventService.eventDetail(Integer.parseInt(Id));
            if(result.isEmpty()) return ResponseEntity.noContent().build();
            loggerForPlayyoController.warn(" getEntity " + result.get());
        }catch(NullPointerException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // return new ResponseEntity<>(convertEventToResponseEventDto(result.get()), HttpStatus.OK);
        return ResponseEntity.ok(result.get().getEventName() + " " + result.get().getOrganizer().getUsername() );
    }

    @PostMapping("/joinEvent")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> joinEvent(@Valid @RequestBody RequestJoinEvent requestJoinEvent){
        Integer uniqueEventId = Integer.parseInt(requestJoinEvent.getUniqueid());
        String  usernameWhoWantToJoinEvent = requestJoinEvent.getUsername();
        boolean response = eventService.userJoinsAnEvent(uniqueEventId, usernameWhoWantToJoinEvent);
        return ResponseEntity.ok(response);
    }
}