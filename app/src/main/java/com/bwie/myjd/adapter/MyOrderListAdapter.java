package com.bwie.myjd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bwie.myjd.PayActivity;
import com.bwie.myjd.R;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.jsonbean.MyOrder;
import com.bwie.myjd.toast.ToastShow;
import com.example.myokhttp.CallbackInterface;
import com.example.myokhttp.Method;
import com.example.myokhttp.OkRequestUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Time:2017/9/29 20:08
 * Author:王才文
 * Description:
 */

public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.MyViewHolder> {

    private Context context;
    private List<MyOrder.DataBean> list;
    private OnItemClickListener mOnItemClickListener;
    private final LayoutInflater from;


    public MyOrderListAdapter(Context context, List<MyOrder.DataBean> list) {
        this.context = context;
        this.list = list;

        from = LayoutInflater.from(context);
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = null;

            View view = from.inflate(R.layout.order_item, null);
            holder = new MyViewHolder(view);



        return holder;
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.orderID.setText("订单ID:"+list.get(position).orderid);
        holder.orderPrice.setText("实付款:"+list.get(position).price);
        holder.orderTime.setText("创建时间:"+list.get(position).createtime);
        if(list.get(position).status==0){
            holder.orderStatus.setText("支付状态:待支付");
            holder.orderZhifu.setVisibility(View.VISIBLE);
        }else if(list.get(position).status==1){
            holder.orderStatus.setText("支付状态:已支付");
            holder.orderZhifu.setVisibility(View.GONE);
        }else{
            holder.orderStatus.setText("支付状态:已取消");
            holder.orderZhifu.setVisibility(View.GONE);
        }

        holder.orderZhifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderid = list.get(position).orderid;
                Intent intent = new Intent(context, PayActivity.class);
                context.startActivity(intent);
                updateorder(orderid);
            }
        });



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

    private void updateorder(int orderid) {
        Map<String, String> map = new HashMap<>();
        map.put("uid",169+"");
        map.put("status",1+"");
        map.put("orderId",orderid+"");
        OkRequestUtils.call(Api.UPDATE_ORDER, Method.POST,map);
        OkRequestUtils.getInstance(context).setCallback(new CallbackInterface() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject =new JSONObject(string);
                    String msg = jsonObject.getString("msg");
                    ToastShow.showToast(context,msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderID;
        TextView orderTime;
        TextView orderPrice;
        TextView orderStatus;
        Button orderZhifu;
        Button zaiciGoumai;
        public MyViewHolder(View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.tv_order_id);
            orderTime = itemView.findViewById(R.id.tv_order_create_time);
            orderPrice = itemView.findViewById(R.id.tv_order_sumprice);
            orderStatus = itemView.findViewById(R.id.tv_order_status);
            orderZhifu = itemView.findViewById(R.id.bt_quzhifu);
            zaiciGoumai = itemView.findViewById(R.id.bt_zaicigoumai);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

}
