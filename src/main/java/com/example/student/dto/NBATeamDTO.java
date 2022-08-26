package com.example.student.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

public class NBATeamDTO {

    private long id;
    private String name;
    private String fullName;
    private String city;

    private NBATeamDTO(long id, String name, String fullName, String city) {
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

    public static NBATeamDTO convertToDto(ResponseEntity<String> response){
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(response.getBody());
            return new NBATeamDTO(root.path("id").asLong(), root.path("name").asText(),
                    root.path("full_name").asText(), root.path("city").asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
