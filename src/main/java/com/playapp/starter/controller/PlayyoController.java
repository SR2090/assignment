package com.playapp.starter.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playapp.starter.data.Event;
import com.playapp.starter.exchange.ResponseEventDto;
import com.playapp.starter.service.EventService;

// conversion of reposiotry response to api response
// DTO object
@RestController
@RequestMapping("/v1")
public class PlayyoController {

    private static final Logger loggerForPlayyoController = LoggerFactory.getLogger(PlayyoController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public String sayHello() {
        loggerForPlayyoController.warn("SENDING RESPONSE sayHello");
        return "text";
    }

    @GetMapping("/events")
    @PreAuthorize("hasRole('ROLE_USER')")
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
            sb.append("+");
        }
        // return new ResponseEntity<>(convertEventsToResponseEventDtos(result.get()), HttpStatus.OK);
        return ResponseEntity.ok(sb.toString());
    }

    // This handles the details about individual event
	@GetMapping("/event-details/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
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

    private ResponseEventDto convertEventToResponseEventDto(Event eventToConvert) {
        TypeMap<Event, ResponseEventDto> propertyMapper = this.modelMapper.createTypeMap(Event.class, ResponseEventDto.class);
        propertyMapper.addMappings(mapper -> mapper.using(new EventOrganizerToReponseEventOrganizerConverter())
                            .map(usr -> usr.getOrganizer().getUsername(), ResponseEventDto::setOrganizerName));
        propertyMapper.addMappings(mapper -> mapper.using(new ListOfUsersInEventToResponseEventDTO())
                                    .map(Event::getUsers, ResponseEventDto::setUserList));

        ResponseEventDto result = modelMapper.map(eventToConvert, ResponseEventDto.class);
        return result;
    }

    private List<ResponseEventDto> convertEventsToResponseEventDtos(List<Event> eventsToConvert){
        // setup the converter
        // Need to specify how to convert List of users to List Of String
        TypeMap<Event, ResponseEventDto> propertyMapper = this.modelMapper.createTypeMap(Event.class, ResponseEventDto.class);
        propertyMapper.addMappings(mapper -> mapper.using(new ListOfUsersInEventToResponseEventDTO())
                                    .map(Event::getUsers, ResponseEventDto::setUserList));
        propertyMapper.addMappings(mapper -> mapper.using(new EventOrganizerToReponseEventOrganizerConverter())
                                    .map(usr -> usr.getOrganizer().getUsername(), ResponseEventDto::setOrganizerName));


        List<ResponseEventDto> results = eventsToConvert
                                        .stream()
                                        .map(element -> modelMapper.map(element, ResponseEventDto.class))
                                        .collect(Collectors.toList());
        return results;
    }

}