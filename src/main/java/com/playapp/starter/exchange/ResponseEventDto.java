package com.playapp.starter.exchange;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class ResponseEventDto {
    private String eventName;
    private String createdAt;
    private String organizerName;
    private List<String> userList;
}
