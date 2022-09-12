package com.example.student.service;

import com.example.student.configuration.NbaApiConfiguration;
import com.example.student.model.dto.NBAPlayerDTO;
import com.example.student.model.dto.NBATeamDTO;
import com.example.student.controller.exception.NoContentException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ForeignAPIService {

    @Value("${nba.player_url}")
    private String resourceUrl;
    private final NbaApiConfiguration nbaApiConfiguration;

    public ForeignAPIService(NbaApiConfiguration nbaApiConfiguration) {
        this.nbaApiConfiguration = nbaApiConfiguration;
    }


    public NBAPlayerDTO getPlayer(long id){
        RestTemplate restTemplate = new RestTemplate();
        String playerId = Long.toString(id);

        resourceUrl += playerId;

        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        if (response.getStatusCode() == HttpStatus.OK){
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(response.getBody());
                return new NBAPlayerDTO(root.path("id").asLong(), root.path("first_name").asText(),
                        root.path("last_name").asText(), root.path("position").asText(),
                        root.path("team").path("name").asText());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }else{
            return null;
        }
    }

    public NBATeamDTO getTeam(long id){
        WebClient webClient = WebClient.builder().baseUrl(nbaApiConfiguration.getTeamUrl()).build();
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.method(HttpMethod.GET);

        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/{id}",id);

        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("");

        WebClient.ResponseSpec responseSpec = headersSpec.header(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE).retrieve();

        Mono<String> monoResponse = headersSpec.exchangeToMono(clientResponse -> {
            if(clientResponse.statusCode().equals(HttpStatus.OK)){
                return clientResponse.bodyToMono(String.class);
            }else{
                System.out.println(clientResponse.statusCode());
                throw new NoContentException();
            }
        });

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
