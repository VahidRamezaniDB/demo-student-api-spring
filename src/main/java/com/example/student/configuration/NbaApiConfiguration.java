package com.example.student.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;


@ConstructorBinding
@ConfigurationProperties(prefix = "nba")
public class NbaApiConfiguration {
    private String playerUrl;
    private String teamUrl;

    public NbaApiConfiguration(String playerUrl, String teamUrl) {
        this.playerUrl = playerUrl;
        this.teamUrl = teamUrl;
    }

    public String getPlayerUrl() {
        return playerUrl;
    }

    public String getTeamUrl() {
        return teamUrl;
    }

    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }

    public void setTeamUrl(String teamUrl) {
        this.teamUrl = teamUrl;
    }
}
