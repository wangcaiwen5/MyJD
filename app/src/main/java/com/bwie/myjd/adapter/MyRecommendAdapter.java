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
import com.bwie.myjd.jsonbean.GetAd;

import java.util.List;

/**
 * Time:2017/9/29 20:08
 * Author:王才文
 * Description:
 */

public class MyRecommendAdapter extends RecyclerView.Adapter<MyRecommendAdapter.MyViewHolder> {

    private Context context;
    private List<GetAd.TuijianBean.ListBean> list;
    private OnItemClickListener mOnItemClickListener;
    private final LayoutInflater from;

    public MyRecommendAdapter(Context context, List<GetAd.TuijianBean.ListBean> list) {
        this.context = context;
        this.list = list;

        from = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = from.inflate(R.layout.frist_recommend_item, null);
       MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.recommend_msg.setText(list.get(position).title+"");

        holder.recommend_price.setText("¥"+list.get(position).price+"");
        String images = list.get(position).images;
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.recommend_img);

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
        ImageView recommend_img;
        TextView recommend_msg;
        TextView recommend_price;
        public MyViewHolder(View itemView) {
            super(itemView);
            recommend_img = itemView.findViewById(R.id.iv_recommend_img);
            recommend_msg = itemView.findViewById(R.id.tv_recommend_msg);
            recommend_price = itemView.findViewById(R.id.tv_recommend_price);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
