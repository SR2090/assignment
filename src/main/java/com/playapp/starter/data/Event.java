package com.playapp.starter.data;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Document(collection = "Events")
public class Event {
    @Field("_id")
    private String mongoEventId;
    @Field("UniqueId")
    private Integer integerId;
    @Indexed(name = "Name of event", unique = true)
    private String eventName;
    @CreatedDate
    private Instant createdAt;
    @Field("Participants")
    private List<User> users;
    @Field("Organizer")
    private User organizer;
    // if the event has started then 
    // no new user can be added to the event
    // handle during adding users to event
    @Field("Status")
    private EventStatus statusOfEvent;
}
