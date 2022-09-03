package com.example.student.service;

import com.example.student.configuration.NbaApiConfiguration;
import com.example.student.dto.NBAPlayerDTO;
import com.example.student.dto.NBATeamDTO;
import com.example.student.exception.NoContentException;
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
    private NbaApiConfiguration nbaApiConfiguration;

    public ForeignAPIService(NbaApiConfiguration nbaApiConfiguration) {
        this.nbaApiConfiguration = nbaApiConfiguration;
    }


    public NBAPlayerDTO getPlayer(long id){
        RestTemplate restTemplate = new RestTemplate();
        String playerId = Long.toString(id);

        resourceUrl += playerId;

        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        if (response.getStatusCode() == HttpStatus.OK){
            return NBAPlayerDTO.convertToDto(response);
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

        Mono<String> response = headersSpec.exchangeToMono(clientResponse -> {
            if(clientResponse.statusCode().equals(HttpStatus.OK)){
                return clientResponse.bodyToMono(String.class);
            }else{
                System.out.println(clientResponse.statusCode());
                throw new NoContentException();
            }
        });
//        Mono<String> response = responseSpec.bodyToMono(String.class);

        return NBATeamDTO.convertToDto(response);

    }
}
