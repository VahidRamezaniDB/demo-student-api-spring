package com.example.student.service;

import com.example.student.exception.NoContentException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public class SelfService {

    public void callSelfApi(long id){
//        WebClient webClient = WebClient.builder().baseUrl(configuration.getStudentUrl()).build();
        WebClient webClient = WebClient.builder().baseUrl("http://127.0.0.1:8080/student").build();
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.method(HttpMethod.GET);

        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/{id}", id);

        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("");

        Mono<String> response = headersSpec.exchangeToMono(clientResponse -> {
            if(clientResponse.statusCode().equals(HttpStatus.OK)){
                return clientResponse.bodyToMono(String.class);
            }else{
                System.out.println(clientResponse.statusCode());
                throw new NoContentException();
            }
        });
        System.out.println(response.block());
    }

    public String getRandomStudentName(){
        RestTemplate restTemplate = new RestTemplate();
        int upperBond = 7;
        String playerId = Integer.toString(new Random().nextInt(upperBond)+1);

        String resourceUrl = "http://localhost:8080/student/";
        resourceUrl += playerId;

        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        if (response.getStatusCode() == HttpStatus.OK){
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(response.getBody());
                return root.path("name").asText();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }else{
            return "Failed.";
        }
    }
}
