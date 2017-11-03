package com.bwie.myjd.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.myjd.R;
import com.bwie.myjd.jsonbean.GetAd;

import java.util.List;

/**
 * Time:2017/9/29 20:08
 * Author:王才文
 * Description:
 */

public class MySecondKillAdapter extends RecyclerView.Adapter<MySecondKillAdapter.MyViewHolder> {

    private Context context;
    private List<GetAd.MiaoshaBean.ListBeanX> list;
    private final LayoutInflater from;

    public MySecondKillAdapter(Context context, List<GetAd.MiaoshaBean.ListBeanX> list) {
        this.context = context;
        this.list = list;

        from = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = from.inflate(R.layout.second_kill_item, null);
       MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.nowpric.setText("¥"+list.get(position).price+"");
        //添加删除线
        holder.beginprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.beginprice.setText("¥"+list.get(position).bargainPrice+"");
        String[] split = list.get(position).images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView nowpric;
        TextView beginprice;
        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_second_img);
            nowpric = itemView.findViewById(R.id.tv_second_nowprice);
            beginprice = itemView.findViewById(R.id.tv_second_beginprice);
        }
    }
}
