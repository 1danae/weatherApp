package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weatherapp.bean.CitySetting;
import com.example.weatherapp.bean.WeatherInfo;
import com.example.weatherapp.db.DatabaseBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CityManagerAdapter extends BaseAdapter {
    Context context;
    List<DatabaseBean> databaseBeanList;

    public CityManagerAdapter(Context context, List<DatabaseBean> databaseBeanList) {
        this.context = context;
        this.databaseBeanList = databaseBeanList;
    }

    @Override
    public int getCount() {
        return databaseBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return databaseBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city_manager,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        DatabaseBean databaseBean = databaseBeanList.get(position);
        WeatherInfo weatherInfo = new Gson().fromJson(databaseBean.getContent(), WeatherInfo.class);
        if(weatherInfo==null||weatherInfo.getNow()==null){
            return convertView;
        }
        holder.cityTv.setText(databaseBean.getCity());
        holder.conTv.setText("天气:"+weatherInfo.getNow().getText());
        holder.windTv.setText(weatherInfo.getNow().getWindDir());
        holder.tempRangeTv.setText(weatherInfo.getNow().getTemp()+"℃");
        return convertView;
    }

    class ViewHolder{
        TextView cityTv,conTv,windTv,tempRangeTv;
        public ViewHolder(View itemView){
            cityTv = itemView.findViewById(R.id.item_city_tv_city);
            conTv = itemView.findViewById(R.id.item_city_tv_condition);
            windTv = itemView.findViewById(R.id.item_city_wind);
            tempRangeTv = itemView.findViewById(R.id.item_city_temprange);

        }
    }
}
