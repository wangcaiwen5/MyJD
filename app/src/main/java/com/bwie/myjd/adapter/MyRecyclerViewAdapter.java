package com.bwie.myjd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.myjd.R;
import com.bwie.myjd.jsonbean.Classfiy;

import java.util.List;

/**
 * Time:2017/9/29 20:08
 * Author:王才文
 * Description:
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Classfiy.DataBean> list;
    private final LayoutInflater from;

    public MyRecyclerViewAdapter(Context context, List<Classfiy.DataBean> list) {
        this.context = context;
        this.list = list;

        from = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = from.inflate(R.layout.recyclerview_item, null);
       MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).name);
        Glide.with(context).load(list.get(position).icon).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_img);
            name = itemView.findViewById(R.id.tv_name);
        }
    }
}
