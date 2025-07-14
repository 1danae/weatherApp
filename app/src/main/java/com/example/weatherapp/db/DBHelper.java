package com.example.weatherapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context,"weather.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        创建表的操作
       String sql = "create table weatherInfo(_id integer primary key autoincrement,city varchar(20)  not null,content text not null,adm varchar(20),city_code varchar(20) unique not null)";
       db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
