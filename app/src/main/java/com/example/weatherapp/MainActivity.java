package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.weatherapp.bean.AreaLocationBean;
import com.example.weatherapp.db.DBHelper;
import com.example.weatherapp.db.DBManager;
import com.example.weatherapp.db.DatabaseBean;
import com.example.weatherapp.utils.URLUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View tv_city;
    private View menu_btn;
    private List<Fragment> fragmentList;
    private List<AreaLocationBean> cityList;
    private FragmentCityAdapter fragmentCityAdapter;
    private ViewPager main_vp;
    private ImageView setting;
    List<ImageView>imgList;
    private LinearLayout pointLayout;
    private List<DatabaseBean> databaseBeans;
    private int now_bg=R.drawable.xiangrikui;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_city = findViewById(R.id.tv_city);
        menu_btn = findViewById(R.id.menu);
        main_vp = (ViewPager) findViewById(R.id.main_vp);
        setting = (ImageView) findViewById(R.id.IV_set);
        pointLayout = (LinearLayout)findViewById(R.id.main_layout_point);
        setting.setOnClickListener(this);
        fragmentList = new ArrayList<>();
        cityList = new ArrayList<>();
        imgList = new ArrayList<>();
//        DBHelper dbHelper=new DBHelper(this);
        DBManager.initDB(this);
        databaseBeans = DBManager.queryAllInfo();
        for (DatabaseBean databaseBean : databaseBeans) {
            Log.e("TAG", "onCreate: "+databaseBean.toString() );
        }
//        DBManager.deleteAllInfo();
        initPager();
//        initPoint();
//
        fragmentCityAdapter=new FragmentCityAdapter(this.getSupportFragmentManager(),fragmentList);
        main_vp.setAdapter(fragmentCityAdapter);
    }
    private void initPager() {
        if(databaseBeans.size()==0){
            DatabaseBean databaseBean=new DatabaseBean();
            databaseBean.setCity_code("101010100");
            databaseBean.setCity("北京市");
            databaseBean.setAdm("北京市");
            databaseBean.setContent("{\n" +
                    "    \"code\": \"200\",\n" +
                    "    \"updateTime\": \"2023-09-17T17:37+08:00\",\n" +
                    "    \"fxLink\": \"https://www.qweather.com/weather/wuhou-101270119.html\",\n" +
                    "    \"now\": {\n" +
                    "        \"obsTime\": \"2023-09-17T17:22+08:00\",\n" +
                    "        \"temp\": \"25\",\n" +
                    "        \"feelsLike\": \"25\",\n" +
                    "        \"icon\": \"104\",\n" +
                    "        \"text\": \"阴\",\n" +
                    "        \"wind360\": \"356\",\n" +
                    "        \"windDir\": \"北风\",\n" +
                    "        \"windScale\": \"2\",\n" +
                    "        \"windSpeed\": \"11\",\n" +
                    "        \"humidity\": \"74\",\n" +
                    "        \"precip\": \"0.0\",\n" +
                    "        \"pressure\": \"950\",\n" +
                    "        \"vis\": \"16\",\n" +
                    "        \"cloud\": \"91\",\n" +
                    "        \"dew\": \"20\"\n" +
                    "    },\n" +
                    "    \"refer\": {\n" +
                    "        \"sources\": [\n" +
                    "            \"QWeather\"\n" +
                    "        ],\n" +
                    "        \"license\": [\n" +
                    "            \"CC BY-SA 4.0\"\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}");
            databaseBeans.add(databaseBean);
            DBManager.addCityInfo(databaseBean);
        }

        for (int i =0; i < databaseBeans.size();i++){
            ScrollingFragment cwFrag = new ScrollingFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("city",databaseBeans.get(i));
            cwFrag.setArguments(bundle);
            fragmentList.add(cwFrag);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.IV_set){
            Intent intent=new Intent(this,CitySettingActivity.class);
            startActivity(intent);
        }
    }
    private void initPoint() {
//        创建滑动小圆点图标 ViewPager页面指示器的函数
        for (int i = 0; i < fragmentList.size(); i++) {
            ImageView pIv = new ImageView(this);
            pIv.setImageResource(R.mipmap.a1);
            pIv.setMinimumHeight(20);
            pIv.setMinimumWidth(20);
            pIv.setScaleType(ImageView.ScaleType.FIT_XY);
            pIv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) pIv.getLayoutParams();
            lp.setMargins(0,0,20,0);
            imgList.add(pIv);
            pointLayout.addView(pIv);
        }
        imgList.get(imgList.size()-1).setImageResource(R.mipmap.a2);

    }


    @Override
    protected void onRestart() {
        fragmentList.clear();
        DBManager.initDB(this);
        databaseBeans.clear();
      databaseBeans=DBManager.queryAllInfo();

      initPager();
      fragmentCityAdapter.notifyDataSetChanged();
        pointLayout.removeAllViews();
        super.onRestart();
    }
}