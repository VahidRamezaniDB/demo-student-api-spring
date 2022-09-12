package com.example.student.model.dto;

public class SchoolDistanceDTO {
    private String school1Name;
    private String school2Name;
    private double distance;

    public String getSchool1Name() {
        return school1Name;
    }

    public void setSchool1Name(String school1Name) {
        this.school1Name = school1Name;
    }

    public String getSchool2Name() {
        return school2Name;
    }

    public void setSchool2Name(String school2Name) {
        this.school2Name = school2Name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
