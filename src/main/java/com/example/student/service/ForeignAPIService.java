package com.example.student.service;

import com.example.student.dto.NBAPlayerDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class ForeignAPIService {

    @Value("${nbaApi.player.url}")
    private String resourceUrl;

    public NBAPlayerDTO getPlayer(long id){
        RestTemplate restTemplate = new RestTemplate();
        String playerId = Long.toString(id);

        resourceUrl += playerId;

        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        if (response.getStatusCode() == HttpStatus.OK){
            return NBAPlayerDTO.ConvertToDto(response);
        }else{
            return null;
        }
    }
}
