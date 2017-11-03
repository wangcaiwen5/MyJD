package com.bwie.myjd.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bwie.myjd.toast.ToastShow;

/**
 * Time:2017/10/16 20:25
 * Author:王才文
 * Description:
 */

public class DetailsPageAdapter extends PagerAdapter {
    private   String[] split;
    private Context context;

    public DetailsPageAdapter(String[] split, Context context) {
        this.split = split;
        this.context = context;
    }

    @Override
    public int getCount() {
        return split.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView imageView = new ImageView(context);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastShow.showToast(context,"点击了"+position);
            }
        });
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(split[position]).into(imageView);
        container.addView(imageView);


        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }
}
