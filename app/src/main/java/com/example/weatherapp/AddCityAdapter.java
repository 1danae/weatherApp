package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weatherapp.bean.AddCityBean;
import com.example.weatherapp.bean.Location;

import java.util.List;

public class AddCityAdapter extends BaseAdapter {
    private List<Location> addCityBeanList;
    Context context;

    public AddCityAdapter(List<Location> addCityBeanList, Context context) {
        this.addCityBeanList = addCityBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return addCityBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return addCityBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddCityAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_add,null);
            holder = new AddCityAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AddCityAdapter.ViewHolder) convertView.getTag();
        }
        Location addCityBean = addCityBeanList.get(position);
        holder.add_tv_city.setText(addCityBean.getName());
        holder.tv_country.setText(addCityBean.getCountry());
        holder.tv_province.setText(addCityBean.getAdm1());
        holder.location_city.setText("经纬"+addCityBean.getLat()+"|"+addCityBean.getLon());
        return convertView;
    }

    class ViewHolder {

        private final TextView location_city;
        private  TextView tv_province;
        private  TextView add_tv_city;
        private  TextView tv_country;

        public ViewHolder(View itemView){
            add_tv_city = (TextView) itemView.findViewById(R.id.item_city_tv_city);
            tv_country = (TextView)itemView.findViewById(R.id.tv_country);
            tv_province = (TextView)itemView.findViewById(R.id.tv_province);
            location_city = (TextView) itemView.findViewById(R.id.item_city_location);
        }
    }
}

