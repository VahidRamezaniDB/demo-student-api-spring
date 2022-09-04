package com.example.student.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class NBATeamDTO {

    private long id;
    private String name;
    private String fullName;
    private String city;

    public NBATeamDTO(long id, String name, String fullName, String city) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCity() {
        return city;
    }

}
