package com.bwie.myjd.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.myjd.R;
import com.bwie.myjd.jsonbean.ChildList;

import java.util.List;

/**
 * Time:2017/9/29 20:08
 * Author:王才文
 * Description:
 */

public class MyChildListAdapter extends RecyclerView.Adapter<MyChildListAdapter.MyViewHolder> {

    private Context context;
    private List<ChildList.DataBean> list;
    private OnItemClickListener mOnItemClickListener;
    private final LayoutInflater from;
    private int type=0;

    public MyChildListAdapter(Context context, List<ChildList.DataBean> list) {
        this.context = context;
        this.list = list;

        from = LayoutInflater.from(context);
    }

    public void setType(int type){
        this.type=type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = null;
        if(type==0){
            View view = from.inflate(R.layout.child_classify_list_item, null);
            holder = new MyViewHolder(view);
        }else{
            View view = from.inflate(R.layout.child_classify_list_item2, null);
            holder = new MyViewHolder(view);
        }


        return holder;
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String images = list.get(position).images;
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.list_img);

        holder.list_msg.setText(list.get(position).title);
        holder.list_price.setText("¥"+list.get(position).price);
        holder.list_price.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗



        //判断是否设置了监听器
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView list_img;
        TextView list_msg;
        TextView list_price;
        public MyViewHolder(View itemView) {
            super(itemView);
            list_img = itemView.findViewById(R.id.iv_shopplist_img);
            list_msg = itemView.findViewById(R.id.tv_shopplist_msg);
            list_price = itemView.findViewById(R.id.tv_shopplist_price);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
