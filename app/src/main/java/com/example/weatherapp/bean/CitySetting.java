package com.example.weatherapp.bean;

public class CitySetting {
    private String cityName;
    private String weatherStatus;
    private String weatherWind;
    private String weatherTemperature;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public String getWeatherWind() {
        return weatherWind;
    }

    public void setWeatherWind(String weatherWind) {
        this.weatherWind = weatherWind;
    }

    public String getWeatherTemperature() {
        return weatherTemperature;
    }

    public void setWeatherTemperature(String weatherTemperature) {
        this.weatherTemperature = weatherTemperature;
    }
}
