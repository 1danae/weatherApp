package com.example.weatherapp.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.weatherapp.bean.CityLocation;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    public static SQLiteDatabase database;
    /* 初始化数据库信息*/
    public static void initDB(Context context){
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase(); //获取数据库对象
    }
    /* 查找数据库当中城市列表*/
    public static List<String>queryAllCityName(){
        Cursor cursor = database.query("weatherInfo", null, null, null, null, null,null);
        List<String>cityList = new ArrayList<>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("city"));
            cityList.add(city);
        }
        return cityList;
    }
    /* 根据城市名称，替换信息内容*/
    public static int updateInfoByCity(String city_code,String content){
        ContentValues values = new ContentValues();
        values.put("content",content);
        return database.update("weatherInfo",values,"city_code=?",new String[]{city_code});
    }
    /* 新增一条城市记录*/
    public static long  addCityInfo(DatabaseBean databaseBean){
        ContentValues values = new ContentValues();
        values.put("city",databaseBean.getCity());
        values.put("city_code",databaseBean.getCity_code());
        values.put("adm",databaseBean.getAdm());
        values.put("content",databaseBean.getContent());
        return database.insert("weatherInfo",null,values);
    }
    /* 根据城市名，查询数据库当中的内容*/
    public static String queryInfoByCity(String city){
        Cursor cursor = database.query("weatherInfo", null, "city=?", new String[]{city}, null, null, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
            return content;
        }
        return null;
    }

    public static boolean queryInfoByCity_code(String city_code){
        Cursor cursor = database.query("weatherInfo", null, "city_code=?", new String[]{city_code}, null, null, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
            return true;
        }
        return false;
    }

    /* 存储城市天气要求最多存储5个城市的信息，一旦超过5个城市就不能存储了，获取目前已经存储的数量*/
    public static int getCityCount(){
        Cursor cursor = database.query("weatherInfo", null, null, null, null, null, null);
        int count = cursor.getCount();
        return count;
    }
    public static DatabaseBean queryAllInfoByCityCode(String city_code) {
        Cursor cursor = database.query("weatherInfo", null, "city_code=?", new String[]{city_code}, null, null, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
            @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("city"));
            @SuppressLint("Range") String cityCode = cursor.getString(cursor.getColumnIndex("city_code"));
            @SuppressLint("Range") String adm = cursor.getString(cursor.getColumnIndex("adm"));
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
            DatabaseBean bean = new DatabaseBean(id, city, cityCode, adm,content);
            return bean;
        }
        return null;
    }

    /* 查询数据库当中的全部信息*/
    public static List<DatabaseBean>queryAllInfo() {
        Cursor cursor = database.query("weatherInfo", null, null, null, null, null, null);
        List<DatabaseBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
            @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("city"));
            @SuppressLint("Range") String cityCode = cursor.getString(cursor.getColumnIndex("city_code"));
            @SuppressLint("Range") String adm = cursor.getString(cursor.getColumnIndex("adm"));
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
            DatabaseBean bean = new DatabaseBean(id, city, cityCode, adm,content);
            list.add(bean);
        }
        return list;
    }
    /* 根据城市名称，删除这个城市在数据库当中的数据*/
    public static int deleteInfoByCity(String city){
        return database.delete("weatherInfo","city=?",new String[]{city});
    }

    /* 删除表当中所有的数据信息*/
    public static void deleteAllInfo(){
        String sql = "delete from weatherInfo"; //表本身依然存在
        database.execSQL(sql);
    }
}
