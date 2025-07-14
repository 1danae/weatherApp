package com.example.weatherapp.db;

import java.io.Serializable;

public class DatabaseBean implements Serializable {
    private int _id; //数据库的主键
    private String city; //存储的城市
    private String city_code;
    private String adm;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    @Override
    public String toString() {
        return "DatabaseBean{" +
                "_id=" + _id +
                ", city='" + city + '\'' +
                ", city_code='" + city_code + '\'' +
                ", adm='" + adm + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public DatabaseBean(int _id, String city, String city_code, String adm,String content) {
        this._id = _id;
        this.city = city;
        this.city_code = city_code;
        this.adm = adm;
        this.content=content;
    }

    public DatabaseBean() {
    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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
}
