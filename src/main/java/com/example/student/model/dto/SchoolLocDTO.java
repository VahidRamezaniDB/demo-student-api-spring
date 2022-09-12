package com.example.student.model.dto;


import org.locationtech.jts.geom.Point;

public class SchoolLocDTO {

    private String name;
    private String address;
    private Point location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
