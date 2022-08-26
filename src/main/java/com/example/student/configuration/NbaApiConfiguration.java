package com.example.student.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "nba")
public class NbaApiConfiguration {
    private NbaPlayerConfiguration player;
    private NbaTeamConfiguration team;

    public NbaApiConfiguration(NbaPlayerConfiguration player, NbaTeamConfiguration team) {
        this.player = player;
        this.team = team;
    }

    public NbaPlayerConfiguration getPlayer() {
        return player;
    }

    public NbaTeamConfiguration getTeam() {
        return team;
    }

    public void setPlayer(NbaPlayerConfiguration player) {
        this.player = player;
    }

    public void setTeam(NbaTeamConfiguration team) {
        this.team = team;
    }
}
