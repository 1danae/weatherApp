package com.example.weatherapp.bean;

import java.io.Serializable;

public class AreaLocationBean implements Serializable {
    private String[] area;
    private String location;

    public AreaLocationBean(String[] area, String location) {
        this.area = area;
        this.location = location;
    }

    public AreaLocationBean() {

    }

    public String[] getArea() {
        return area;
    }

    public void setArea(String[] area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
