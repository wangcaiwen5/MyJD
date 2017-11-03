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
import com.bwie.myjd.jsonbean.ChildClassify;

import java.util.List;

/**
 * Time:2017/10/4 15:44
 * Author:王才文
 * Description:
 */

public class MyClassifyDetailsChildAdapter extends RecyclerView.Adapter<MyClassifyDetailsChildAdapter.MyViewHolder>{

        private List<ChildClassify.DataBean.ListBean> list;
        private Context context;
    private OnItemClickListener mOnItemClickListener;
    private final LayoutInflater from;

    public MyClassifyDetailsChildAdapter(List<ChildClassify.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
        from = LayoutInflater.from(context);
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = from.inflate(R.layout.class_details_item, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        holder.mTitle.setText(list.get(position).name);
        Glide.with(context).load(list.get(position).icon).into(holder.mImg);
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

        public ImageView mImg;
        public TextView mTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.iv_child_classify_img);
            mTitle = itemView.findViewById(R.id.tv_child_classify_title);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
