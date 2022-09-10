package com.example.student.model.dto;

public class NBAPlayerDTO {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String position;
    private final String teamName;

    public NBAPlayerDTO(long id, String firstName, String lastName, String position, String teamName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.teamName = teamName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public String getTeamName() {
        return teamName;
    }

}
