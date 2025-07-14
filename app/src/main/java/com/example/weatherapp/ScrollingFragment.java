package com.example.weatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.weatherapp.base.BaseFragment;
import com.example.weatherapp.bean.AirWeatherBean;
import com.example.weatherapp.bean.AreaLocationBean;
import com.example.weatherapp.bean.DaysWeatherBean;
import com.example.weatherapp.bean.HourlyWeatherBean;
import com.example.weatherapp.bean.Indices;
import com.example.weatherapp.bean.WeatherInfo;
import com.example.weatherapp.db.DBManager;
import com.example.weatherapp.db.DatabaseBean;
import com.example.weatherapp.svgutil.SvgSoftwareLayerSetter;
import com.example.weatherapp.utils.URLUtils;
import com.example.weatherapp.utils.Utility;
import com.example.weatherapp.view.AirQualityView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScrollingFragment  extends BaseFragment{
    private DatabaseBean databaseBean;
    private TextView tv_city;
    private TextView tv_temperature;
    private TextView tv_range;
    private TextView tv_area;
    private WeatherInfo weatherInfo;
    private LinearLayout ll_header;
    private LinearLayout hour_forecast;
    private DaysWeatherBean daysWeatherBean;
    private LinearLayout forecastLayout;;
    private AirQualityView air_view;
    private TextView tv_pm10;
    private TextView tv_pm2p5;
    private TextView tv_no2;
    private TextView tv_so2;
    private TextView tv_co;
    private TextView tv_o3;
    private GridView gview;
    private RelativeLayout main_bg;
    private int event=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrolling, container, false);
        initView(view);
//       可以通过activity传值获取到当前fragment加载的是哪个地方的天气情况
        Bundle bundle = getArguments();
        databaseBean = (DatabaseBean) bundle.getSerializable("city");
        tv_city.setText(databaseBean.getCity());
        tv_area.setText(databaseBean.getAdm());
        String city_code = databaseBean.getCity_code();
        String temp_url = URLUtils.getTemp_url(city_code);
        String hours_url = URLUtils.getHours_url(databaseBean.getCity_code());
        String days_url=URLUtils.getDays_url(databaseBean.getCity_code());
        String air_url=URLUtils.getAir_url(databaseBean.getCity_code());
        String life_url=URLUtils.getLife_url(databaseBean.getCity_code());
        Log.e("temp_url", "onCreateView: "+temp_url );
        Log.e("houtse", "onCreateView: "+hours_url );
        Log.e("days", "onCreateView: "+days_url );
        Log.e("air", "onCreateView: "+air_url );
        Log.e("life", "onCreateView: "+life_url );
       getSyn(temp_url,view);
       getHourLyForceCast(hours_url,view);
        showWeatherForecastInfo(days_url,view);
        getAir(air_url,view);
        getLife(life_url,view);
        return view;
    }

    private void getLife(String life_url, View view) {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //创建Request
        Request request = new Request.Builder()
                .url(life_url)//访问连接
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Indices indices = new Gson().fromJson(response.body().string(), Indices.class);
                    if(!indices.getCode().equals("200")){
                        return;
                    }
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            List<Indices.DailyWeather> daily = indices.getDaily();
                            LifeWeatherAdpter lifeWeatherAdpter=new LifeWeatherAdpter(daily,view.getContext());
                            gview.setAdapter(lifeWeatherAdpter);
                        }
                    });
                }
            }
        });
    }

    private void getAir(String air_url, View view) {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //创建Request
        Request request = new Request.Builder()
                .url(air_url)//访问连接
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            private AirWeatherBean airWeatherBean;

            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    airWeatherBean = new Gson().fromJson(response.body().string(), AirWeatherBean.class);
                    if(!airWeatherBean.getCode().equals("200")){
                        return;
                    }
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            AirWeatherBean.Now now = airWeatherBean.getNow();
                            tv_pm10.setText("pm10:       "+now.getPm10());
                            tv_pm2p5.setText("pm2.5:        "+now.getPm2p5());
                            tv_no2.setText("no2:          "+now.getNo2());
                            tv_so2.setText("so2:          "+now.getSo2());
                            tv_co.setText("co:          "+now.getCo());
                            tv_o3.setText("o3:          "+now.getO3());
                           air_view.setCurrentAQI(Integer.parseInt(now.getAqi()));
                        }
                    });
                }
            }
        });
    }

    private void getHourLyForceCast(String hours_url, View view) {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //创建Request
        Request request = new Request.Builder()
                .url(hours_url)//访问连接
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            private HourlyWeatherBean hourlyWeatherBean;

            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    hourlyWeatherBean = new Gson().fromJson(response.body().string(), HourlyWeatherBean.class);
                    if(!hourlyWeatherBean.getCode().equals("200")){
                        return;
                    }
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            updateHourlyData(hourlyWeatherBean,view);
                        }
                    });
                }
            }
        });
    }
    private void showWeatherForecastInfo(String days_url, View view)  {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //创建Request
        Request request = new Request.Builder()
                .url(days_url)//访问连接
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            private HourlyWeatherBean hourlyWeatherBean;

            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    daysWeatherBean = new Gson().fromJson(response.body().string(), DaysWeatherBean.class);
                    if(!daysWeatherBean.getCode().equals("200")){
                        return;
                    }
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            updateDaysData(daysWeatherBean,view);
                        }
                    });
                }
            }
        });
    }

    private void updateDaysData(DaysWeatherBean daysWeatherBean,View layout){


//                 weatherToday = daysWeatherBean.getDaily().get(0);
//                sunArcView.setSunriseTime(weatherToday.sunrise);
//                sunArcView.setSunsetTime(weatherToday.sunset);
//
//                sunriseDate = Utility.strToDate(weatherToday.sunrise);
//                sunsetDate = Utility.strToDate(weatherToday.sunset);

                forecastLayout.removeAllViews();
                for (int i = 1; i < daysWeatherBean.getDaily().size(); i++) {
                    DaysWeatherBean.DailyWeather weatherForecastItem = daysWeatherBean.getDaily().get(i);

                    View view = LayoutInflater.from(layout.getContext()).
                            inflate(R.layout.forecast_item, forecastLayout, false);
                    TextView dayOfWeekText = view.findViewById(R.id.day_of_week_text);
                    TextView dateText = view.findViewById(R.id.date_text);
                    ImageView iconWeather = view.findViewById(R.id.icon_weather);
                    TextView maxminText = view.findViewById(R.id.max_min_text);

                    try {
                        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = inputDateFormat.parse(weatherForecastItem.getFxDate());
                        if (i == 1) {
                            dayOfWeekText.setText("明天");
                        } else {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                            switch (dayOfWeek) {
                                case 0:
                                    dayOfWeekText.setText("周日");
                                    break;
                                case 1:
                                    dayOfWeekText.setText("周一");
                                    break;
                                case 2:
                                    dayOfWeekText.setText("周二");
                                    break;
                                case 3:
                                    dayOfWeekText.setText("周三");
                                    break;
                                case 4:
                                    dayOfWeekText.setText("周四");
                                    break;
                                case 5:
                                    dayOfWeekText.setText("周五");
                                    break;
                                default:
                                    dayOfWeekText.setText("周六");
                            }
                        }

                        SimpleDateFormat outputDateFormat = new SimpleDateFormat("M月d日");
                        dateText.setText(outputDateFormat.format(date));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String url = URLUtils.icon_url(weatherForecastItem.getIconDay());
                    Glide.with(this).as(PictureDrawable.class)
                            .listener(new SvgSoftwareLayerSetter())
                            .load(url).into(iconWeather);

                    String maxmin = weatherForecastItem.getTempMax() + " / " + weatherForecastItem.getTempMin() + "℃";
                    maxminText.setText(maxmin);

                    forecastLayout.addView(view);
                }

    }

    private void updateHourlyData(HourlyWeatherBean hourlyWeatherBean,View layout) {
        hour_forecast.removeAllViews();
        List<HourlyWeatherBean.HourlyData> hourlyDataList = hourlyWeatherBean.getHourly();
        for (HourlyWeatherBean.HourlyData hourlyData : hourlyDataList) {
            View view=LayoutInflater.from(layout.getContext()).inflate(R.layout.item_forcecast_hourly,hour_forecast,false);
            TextView hourText = (TextView)view.findViewById(R.id.hour_text);
            ImageView iconHourLy = (ImageView) view.findViewById(R.id.icon_hourly_weather);
            TextView tempText =(TextView) view.findViewById(R.id.temp_text);
            hourText.setText(hourlyData.getFxTime().substring(11,16));
            tempText.setText(hourlyData.getTemp() + "℃");
            String icon=hourlyData.getIcon();
            String url = URLUtils.icon_url(icon);
            Glide.with(this).as(PictureDrawable.class)
                    .listener(new SvgSoftwareLayerSetter())
                    .load(url).into(iconHourLy);


            hour_forecast.addView(view);
        }
    }

    private void initView(View view) {
        tv_city = (TextView)view.findViewById(R.id.tv_city);
        tv_temperature = (TextView) view.findViewById(R.id.tv_temperature);
        tv_range = (TextView)view.findViewById(R.id.tv_temperature_range);
        tv_area = (TextView)view.findViewById(R.id.tv_area);
        ll_header = (LinearLayout)view.findViewById(R.id.ll_header);
         hour_forecast =(LinearLayout) view.findViewById(R.id.hourly_forecast_layout);
        forecastLayout=view.findViewById(R.id.forecast_layout);
        air_view = (AirQualityView)view.findViewById(R.id.air_quality_view);
        tv_pm10 = (TextView)view.findViewById(R.id.aqi_info_text_pm10);
        tv_pm2p5 = (TextView)view.findViewById(R.id.aqi_info_text_pm2p5);
        tv_no2 = (TextView)view.findViewById(R.id.aqi_info_text_no2);
        tv_so2 = (TextView)view.findViewById(R.id.aqi_info_text_so2);
        tv_co = (TextView)view.findViewById(R.id.aqi_info_text_co);
        tv_o3 = (TextView)view.findViewById(R.id.aqi_info_text_o3);
        gview = (GridView)view.findViewById(R.id.gview);
        main_bg =  (RelativeLayout)getActivity().findViewById(R.id.main_bg);

    }
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if(hidden){
//            changeBg();
//        }
//
//    }

    public void getSyn(final String url,View view) {
                //创建OkHttpClient对象
                OkHttpClient client = new OkHttpClient();
                //创建Request
                Request request = new Request.Builder()
                        .url(url)//访问连接
                        .get()
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String restr=response.body().string();
                            weatherInfo = new Gson().fromJson(restr, WeatherInfo.class);
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(!weatherInfo.getCode().equals("200")){
                                        return;
                                    }
                                    updateData(weatherInfo,restr);
                                }
                            });
                        }
                    }
                });
            }

    private void updateData(WeatherInfo weatherInfo,String content){

        tv_temperature.setText(weatherInfo.getNow().getTemp());
        tv_range.setText(weatherInfo.getNow().getText());
        DBManager.initDB(this.getContext());
        DBManager.updateInfoByCity(databaseBean.getCity_code(),content);
        if(weatherInfo.getNow().getText().contains("晴")){
            event=0;
        }else {
            event=4;
        }
//        changeBg();
    }
    @Override
    public void onResume() {
        super.onResume();
        //TODO now it's visible to user
        changeBg();
    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO now it's invisible to user
    }


    private void changeBg() {
//        DBManager.initDB(this.getContext());
//        DatabaseBean databaseBeanNow = DBManager.queryAllInfoByCityCode(this.databaseBean.getCity_code());
//        Drawable drawablexiangrikui = getResources().getDrawable(R.drawable.xiangrikui);
//        Drawable drawableimg = getResources().getDrawable(R.drawable.img);
//        TransitionDrawable td;
//        WeatherInfo weatherInfo = new Gson().fromJson(databaseBeanNow.getContent(), WeatherInfo.class);
////        0:晴,1,阴天,2,雨天,3多云,4其他
//        if(weatherInfo.getNow().getText().contains("晴")){
//           if(event==4){
//               main_bg.setBackgroundResource(R.drawable.xiangrikui);
//               td = new TransitionDrawable(new Drawable[]{drawablexiangrikui, drawableimg});
//               main_bg.setBackgroundDrawable(td);
//               td.startTransition(1000);
//               event =0;
//           }
//        }else {
//            if(event ==0){
//                main_bg.setBackgroundResource(R.drawable.img);
//                td = new TransitionDrawable(new Drawable[]{drawablexiangrikui, drawableimg});
//                main_bg.setBackgroundDrawable(td);
//                td.startTransition(1000);
//
//            }
//        }
    }

}