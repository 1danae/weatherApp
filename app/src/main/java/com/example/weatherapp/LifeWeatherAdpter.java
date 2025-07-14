package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.bean.Indices;

import java.util.List;

public class LifeWeatherAdpter extends BaseAdapter {
    private List<Indices.DailyWeather> dailyWeatherList;
    Context context;

    public LifeWeatherAdpter(List<Indices.DailyWeather> dailyWeatherList, Context context) {
        this.dailyWeatherList = dailyWeatherList;
        this.context = context;
    }

    @Override
    public int getCount() {
            return dailyWeatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return dailyWeatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
           convertView= LayoutInflater.from(context).inflate(R.layout.life_item,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Indices.DailyWeather dailyWeather =dailyWeatherList.get(position);
        if (dailyWeather != null) {
//            3 1 2 9 5 11
            holder.tv_indices.setText(dailyWeather.getName());
            holder.indices_text.setText(dailyWeather.getCategory());
            if(dailyWeather.getType().equals("1")){
                holder.iv_indices.setImageResource(R.drawable.icon_sport);
            }else  if(dailyWeather.getType().equals("2")){
                holder.iv_indices.setImageResource(R.drawable.icon_car);
            }else   if(dailyWeather.getType().equals("3")){
                holder.iv_indices.setImageResource(R.drawable.icon_cloth);
            }else  if(dailyWeather.getType().equals("5")){
                holder.iv_indices.setImageResource(R.drawable.icon_light);
            } else if(dailyWeather.getType().equals("9")){
                holder.iv_indices.setImageResource(R.drawable.icon_ganmao);
            } else  if(dailyWeather.getType().equals("11")){
                holder.iv_indices.setImageResource(R.drawable.icon_air);
            }
            else {
                holder.iv_indices.setImageResource(R.drawable.icon_car);
            }

        }
        return convertView;
    }
    class ViewHolder {

        private final TextView tv_indices;
        private final ImageView iv_indices;
        private final TextView indices_text;

        public ViewHolder(View itemView){
            tv_indices = (TextView) itemView.findViewById(R.id.tv_indices);
            iv_indices = (ImageView)itemView.findViewById(R.id.iv_indices);
            indices_text = (TextView)itemView.findViewById(R.id.indices_text);
        }
    }
}
