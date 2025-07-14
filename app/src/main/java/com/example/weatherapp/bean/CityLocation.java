package com.example.weatherapp.bean;

public class CityLocation {
    private String city;
    private String city_code;
    private String adm;

    public CityLocation(String city, String city_code, String adm) {
        this.city = city;
        this.city_code = city_code;
        this.adm = adm;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }
}
