package com.example.student.service;

import com.example.student.configuration.NbaApiConfiguration;
import com.example.student.dto.NBAPlayerDTO;
import com.example.student.dto.NBATeamDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class ForeignAPIService {

    @Value("${nbaApi.player.url}")
    private String resourceUrl;
    public ForeignAPIService(NbaApiConfiguration nbaApiConfiguration) {
        this.nbaApiConfiguration = nbaApiConfiguration;
    }

    private NbaApiConfiguration nbaApiConfiguration;

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
        RestTemplate restTemplate = new RestTemplate();
        String teamId = Long.toString(id);
        String teamResourceUrl = nbaApiConfiguration.getTeam().getUrl();

        teamResourceUrl += teamId;

        ResponseEntity<String> response = restTemplate.getForEntity(teamResourceUrl, String.class);

        if (response.getStatusCode() == HttpStatus.OK){
            return NBATeamDTO.convertToDto(response);
        }else{
            return null;
        }
    }
}
