package com.example.weatherapp.bean;

import java.util.List;

public class AddCityBean {
    private String code;
    private List<Location> location;
    private Refer refer;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Location> getLocationList() {
        return location;
    }

    public void setLocationList(List<Location> locationList) {
        this.location = locationList;
    }

    public Refer getRefer() {
        return refer;
    }

    public void setRefer(Refer refer) {
        this.refer = refer;
    }
// Getter and Setter methods for all the fields




    public class Refer {
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
        // Getter and Setter methods for all the fields
    }
}






