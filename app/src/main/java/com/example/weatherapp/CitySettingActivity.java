package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.weatherapp.bean.CitySetting;
import com.example.weatherapp.db.DBManager;
import com.example.weatherapp.db.DatabaseBean;

import java.util.ArrayList;
import java.util.List;

public class CitySettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView city_lv;
    private List<CitySetting> citySettingList;
    private ImageView editCity;
    List<DatabaseBean> databaseBeans=null;
    private CityManagerAdapter cityManagerAdapter;
    private ImageView iv_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_setting);
        DBManager.initDB(this);
        databaseBeans = DBManager.queryAllInfo();
        city_lv = (ListView)findViewById(R.id.city_lv);
        initCityItem(databaseBeans);
        editCity = (ImageView)findViewById(R.id.iv_ed_city);
        iv_add = (ImageView)findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);
        editCity.setOnClickListener(this);
//        Log.i("cityList", "onCreate: "+cityList);
    }

    private void initCityItem(List list){
        cityManagerAdapter = new CityManagerAdapter(this, list);
        city_lv.setAdapter(cityManagerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_ed_city:
                Intent intent=new Intent(this,CityDeleteActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_add:
                Intent intent_add=new Intent(this,AddCityActivity.class);
                startActivity(intent_add);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<DatabaseBean> list = DBManager.queryAllInfo();
        databaseBeans.clear(); //先清空
        databaseBeans.addAll(list);  //再添加
        cityManagerAdapter.notifyDataSetChanged(); //提示更新
    }
}