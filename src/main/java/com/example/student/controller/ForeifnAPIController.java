package com.example.student.controller;

import com.example.student.dto.NBAPlayerDTO;
import com.example.student.dto.NBATeamDTO;
import com.example.student.service.ForeignAPIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nba")
public class ForeifnAPIController {
    private final ForeignAPIService foreignAPIService;

    public ForeifnAPIController(ForeignAPIService foreignAPIService) {
        this.foreignAPIService = foreignAPIService;
    }


    @GetMapping("/player/{id}")
    public ResponseEntity<NBAPlayerDTO> getNBAPlayer(@PathVariable long id){
        return new ResponseEntity<>(foreignAPIService.getPlayer(id), HttpStatus.OK);
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<NBATeamDTO> getNBATeam(@PathVariable long id){
        return new ResponseEntity<>(foreignAPIService.getTeam(id), HttpStatus.OK);
    }
}
