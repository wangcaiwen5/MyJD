package com.bwie.myjd.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Time:2017/10/16 21:01
 * Author:王才文
 * Description:
 */

public class MyTabLayoutAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private Context context;
    private List<String> lsitstr;

    public MyTabLayoutAdapter(FragmentManager fm, List<Fragment> list, Context context, List<String> lsitstr) {
        super(fm);
        this.list = list;
        this.context = context;
        this.lsitstr = lsitstr;
    }



    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lsitstr.get(position);
    }
}
