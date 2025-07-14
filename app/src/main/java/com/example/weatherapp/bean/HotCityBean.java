package com.example.weatherapp.bean;

import java.util.List;

public class HotCityBean {
    private String code;
  private  List<Location> topCityList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Location> getTopCityList() {
        return topCityList;
    }

    public void setTopCityList(List<Location> topCityList) {
        this.topCityList = topCityList;
    }

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
