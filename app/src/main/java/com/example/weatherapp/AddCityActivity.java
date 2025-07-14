package com.example.weatherapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weatherapp.bean.AddCityBean;
import com.example.weatherapp.bean.HotCityBean;
import com.example.weatherapp.bean.Indices;
import com.example.weatherapp.bean.Location;
import com.example.weatherapp.bean.WeatherInfo;
import com.example.weatherapp.db.DBManager;
import com.example.weatherapp.db.DatabaseBean;
import com.example.weatherapp.utils.URLUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddCityActivity extends AppCompatActivity implements  View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText et_search;
    private ImageView iv_search;
    private ListView lv_add;
    private AddCityAdapter addCityAdapter;
private AddCityBean addCityBean=new AddCityBean();
    private List<Location> locationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        et_search = (EditText) findViewById(R.id.et_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        lv_add = (ListView) findViewById(R.id.lv_add);
        iv_search.setOnClickListener(this);
       initLocation();
        lv_add.setOnItemClickListener(this);
    }





    public void  initLocation(){
        OkHttpClient okHttpClient = new OkHttpClient();
        String location_url = URLUtils.getHot_url();
        Request request = new Request.Builder()
                .url(location_url)
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        //异步请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                    Toast.makeText(Okhttp2Activity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseString = response.body().string();
                //在主线程中修改UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //把服务器给我们响应的字符串数据打印出来
                        HotCityBean hotCityBean = new Gson().fromJson(responseString, HotCityBean.class);
                        if(!hotCityBean.getCode().equals("200")){
                            Toast.makeText(getApplicationContext(),"查询失败",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        locationList.clear();
                        locationList.addAll(hotCityBean.getTopCityList());
                        addCityAdapter = new AddCityAdapter(locationList,AddCityActivity.this);
                        lv_add.setAdapter(addCityAdapter);
                    }
                });
            }
        });
    }


    @Override
    public void onClick(View v) {
        String s = et_search.getText().toString();
        if(s.length()<=0){
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        String location_url = URLUtils.getLocation_url(s, new String[]{});
            Request request = new Request.Builder()
                    .url(location_url)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
            //异步请求
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
//                    Toast.makeText(Okhttp2Activity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseString = response.body().string();
                    //在主线程中修改UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //把服务器给我们响应的字符串数据打印出来
                            addCityBean = new Gson().fromJson(responseString, AddCityBean.class);
                            if(!addCityBean.getCode().equals("200")){
                                Toast.makeText(getApplicationContext(),"查询失败",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            locationList.clear();
                            locationList.addAll(addCityBean.getLocationList());
                            addCityAdapter = new AddCityAdapter(locationList,AddCityActivity.this);
                            lv_add.setAdapter(addCityAdapter);
                        }
                    });
                }
            });
        }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("TAG", "onItemLongClick: "+position );
        final String [] arr = {"添加","取消"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon_add);
        builder.setTitle("操作");
        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    DBManager.initDB(getApplicationContext());
                    int cityCount = DBManager.getCityCount();
                    if(cityCount>5){
                        Toast.makeText(getApplicationContext(),"最多添加六个城市",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean flag = DBManager.queryInfoByCity_code(locationList.get(position).getId());
                    if(!flag){
                        OkHttpClient okHttpClient = new OkHttpClient();
                        String location_url = URLUtils.getTemp_url(locationList.get(position).getId());
                        Request request = new Request.Builder()
                                .url(location_url)
                                .cacheControl(CacheControl.FORCE_NETWORK)
                                .build();
                        //异步请求
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
//                    Toast.makeText(Okhttp2Activity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String responseString = response.body().string();
                                        //把服务器给我们响应的字符串数据打印出来
                              WeatherInfo  weatherInfo = new Gson().fromJson(responseString, WeatherInfo.class);
                                        if(!weatherInfo.getCode().equals("200")){
                                            Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                DatabaseBean databaseBean=new DatabaseBean();
                                        databaseBean.setCity(locationList.get(position).getName());
                                databaseBean.setContent(responseString);
                                databaseBean.setCity_code(locationList.get(position).getId());
                                databaseBean.setAdm(locationList.get(position).getAdm1());
                                DBManager.initDB(getApplicationContext());
                               DBManager.addCityInfo(databaseBean);

                            }
                        });

                    }else {
                        Toast.makeText(getApplicationContext(),"您已添加了该城市",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"您选择了:"+arr[which],Toast.LENGTH_SHORT).show();
                }
               
            }
        });
        builder.show();
    }
}
