package com.example.student.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

public class NBAPlayerDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String position;
    private String teamName;

    public NBAPlayerDTO(long id, String firstName, String lastName, String position, String teamName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.teamName = teamName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public String getTeamName() {
        return teamName;
    }

}
