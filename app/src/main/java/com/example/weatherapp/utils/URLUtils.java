package com.example.weatherapp.utils;

public class URLUtils {

//    public static final  String KEY = "c5eb4e8fde624542a2e62f47cb9b6a80";
    public static final  String KEY = "647d84ca5f0f4ff1a444d341287a98b2";
    public static String temp_url = "https://devapi.qweather.com/v7/weather/now?";
    public static final String location_url="https://geoapi.qweather.com/v2/city/lookup?location=";
    public static final String hours_url="https://devapi.qweather.com/v7/weather/24h?location=";
    public static final String days_url="https://devapi.qweather.com/v7/weather/7d?location=";
    public static final String end_url="&key="+KEY;
    public static final String air_url="https://devapi.qweather.com/v7/air/now?location=";
    public static final String life_url="https://devapi.qweather.com/v7/indices/1d?type=1,2,3,9,5,11&location=";
    public static final String  hot_url="https://geoapi.qweather.com/v2/city/top?number=5&range=cn";
    public static String getTemp_url(String area_code){
        return temp_url+"location="+area_code+"&key="+KEY;
    }

    public static  String getLocation_url(String location,String[] area){

        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < area.length; i++) {
            stringBuilder.append("&adm"+(i+1)+"="+area[i].toString());
        }
        return location_url+location+stringBuilder.toString()+"&key="+KEY;
    }

    public static String getHours_url(String location){
        return hours_url+location+"&key="+KEY;
    }

    public static String icon_url(String icon){
        return "https://icons.qweather.com/assets/icons/"+icon+".svg";
    }

    public static String getDays_url(String location){
        return days_url+location+end_url;
    }

    public static  String getAir_url(String location){
        return air_url+location+end_url;
    }


    public static String getLife_url(String city_code) {
        return life_url+city_code+end_url;
    }


    public static  String getHot_url(){
        return hot_url+end_url;
    }
}
