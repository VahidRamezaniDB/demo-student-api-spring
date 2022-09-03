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

    public static NBATeamDTO convertToDto(Mono<String> monoResponse){
        ObjectMapper mapper = new ObjectMapper();
        String response = monoResponse.block();

        System.out.println(response);

        try {
            JsonNode root = mapper.readTree(response);
            return new NBATeamDTO(root.path("id").asLong(), root.path("name").asText(),
                    root.path("full_name").asText(), root.path("city").asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
