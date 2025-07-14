package com.example.weatherapp.bean;

import java.util.List;

public class HourlyWeatherBean {

        private String code;
        private String updateTime;
        private String fxLink;
        private List<HourlyData> hourly;
        private ReferData refer;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
    }

    public List<HourlyData> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyData> hourly) {
        this.hourly = hourly;
    }

    public ReferData getRefer() {
        return refer;
    }

    public void setRefer(ReferData refer) {
        this.refer = refer;
    }
    // Getters and setters for the above fields

        public static class HourlyData {
            public String getFxTime() {
                return fxTime;
            }

            public void setFxTime(String fxTime) {
                this.fxTime = fxTime;
            }

            public int getTemp() {
                return temp;
            }

            public void setTemp(int temp) {
                this.temp = temp;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getWind360() {
                return wind360;
            }

            public void setWind360(String wind360) {
                this.wind360 = wind360;
            }

            public String getWindDir() {
                return windDir;
            }

            public void setWindDir(String windDir) {
                this.windDir = windDir;
            }

            public String getWindScale() {
                return windScale;
            }

            public void setWindScale(String windScale) {
                this.windScale = windScale;
            }

            public int getWindSpeed() {
                return windSpeed;
            }

            public void setWindSpeed(int windSpeed) {
                this.windSpeed = windSpeed;
            }

            public int getHumidity() {
                return humidity;
            }

            public void setHumidity(int humidity) {
                this.humidity = humidity;
            }

            public int getPop() {
                return pop;
            }

            public void setPop(int pop) {
                this.pop = pop;
            }

            public String getPrecip() {
                return precip;
            }

            public void setPrecip(String precip) {
                this.precip = precip;
            }

            public int getPressure() {
                return pressure;
            }

            public void setPressure(int pressure) {
                this.pressure = pressure;
            }

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
            }

            public String getDew() {
                return dew;
            }

            public void setDew(String dew) {
                this.dew = dew;
            }

            private String fxTime;
            private int temp;
            private String icon;
            private String text;
            private String wind360;
            private String windDir;
            private String windScale;
            private int windSpeed;
            private int humidity;
            private int pop;
            private String precip;
            private int pressure;
            private String cloud;
            private String dew;

            // Getters and setters for the above fields
        }

        public static class ReferData {
            private List<String> sources;
            private List<String> license;

            public List<String> getSources() {
                return sources;
            }

            public void setSources(List<String> sources) {
                this.sources = sources;
            }

            public List<String> getLicense() {
                return license;
            }

            public void setLicense(List<String> license) {
                this.license = license;
            }

// Getters and setters for the above fields
        }

}
