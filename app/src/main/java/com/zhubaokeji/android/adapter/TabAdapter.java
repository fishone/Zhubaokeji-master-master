package com.zhubaokeji.android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhubaokeji.android.view.JpOrderActivity;

import java.util.ArrayList;

/**
 * Created by fisho on 2017/4/26.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;


    public TabAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //设置tablayout标题
    @Override
    public CharSequence getPageTitle(int position) {
        return JpOrderActivity.mTitle.get(position);

    }
}