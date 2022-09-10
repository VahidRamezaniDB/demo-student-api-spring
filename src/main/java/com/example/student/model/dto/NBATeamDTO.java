package com.example.student.model.dto;

public class NBATeamDTO {

    private final long id;
    private final String name;
    private final String fullName;
    private final String city;

    public NBATeamDTO(long id, String name, String fullName, String city) {
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

}
