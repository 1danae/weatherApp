package com.example.weatherapp;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentCityAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragmentList;
   public FragmentCityAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList){
       super(fragmentManager);
       this.fragmentList=fragmentList;
   }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    int childCount = 0;   //表示ViewPager包含的页数
    //    当ViewPager的页数发生改变时，必须要重写两个函数
    @Override
    public void notifyDataSetChanged() {
        this.childCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (childCount>0) {
            childCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
