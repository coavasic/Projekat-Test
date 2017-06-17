package com.example.vasic.projekat.Adapters;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by vasic on 5/6/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm){

        super(fm);

    }

    public void addFragments(Fragment fragmet,  String title){

        this.fragments.add(fragmet);
        this.titles.add(title);

    }



    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int positon){
        return titles.get(positon);
    }
}
