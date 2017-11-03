package com.bwie.myjd.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myjd.jsonbean.Classfiy;

import java.util.List;

/**
 * Time:2017/10/10 15:32
 * Author:王才文
 * Description:
 */

public class ClassifyPagerAdapter extends PagerAdapter {

    private   List<List<Classfiy.DataBean>> list;
    private Context context;

    public ClassifyPagerAdapter(List<List<Classfiy.DataBean>> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RecyclerView recyclerView=new RecyclerView(context);
        recyclerView.setAdapter(new MyRecyclerViewAdapter(context,list.get(position)));

        recyclerView.setLayoutManager(new GridLayoutManager(context,5));
        ViewGroup parent = (ViewGroup) recyclerView.getParent();

        if (parent != null) {

            parent.removeAllViews();

        }
        container.addView(recyclerView);
        return recyclerView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
