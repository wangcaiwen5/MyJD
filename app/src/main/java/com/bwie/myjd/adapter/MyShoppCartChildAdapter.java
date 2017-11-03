package com.bwie.myjd.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.myjd.R;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.bean.CheckBoxChildBean;
import com.bwie.myjd.jsonbean.ShoppCartBean;
import com.bwie.myjd.presenter.RequestrPresenter;
import com.bwie.myjd.view.OkRequestView;
import com.github.nukc.amountview.AmountView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Time:2017/10/4 15:44
 * Author:王才文
 * Description:
 */

public class MyShoppCartChildAdapter extends RecyclerView.Adapter<MyShoppCartChildAdapter.MyViewHolder>{

        private List<ShoppCartBean.DataBean.ListBean> list;
        private Context context;
    List<CheckBoxChildBean> listCBchild;
    private OnItemClickListener mOnItemClickListener;
    private OnItemlongClickListener mOnItemlongClickListener;
    private final LayoutInflater from;
    private final SharedPreferences isLogin;
    private final int uid;
    private  int selected=0;



    public MyShoppCartChildAdapter(List<ShoppCartBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
        isLogin = context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        uid = isLogin.getInt("uid", -1);

        from = LayoutInflater.from(context);
    }



    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemlongClickListener(OnItemlongClickListener mOnItemlongClickListener){
        this.mOnItemlongClickListener = mOnItemlongClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.shopping_cart_childitem, null);
        MyViewHolder holder = new MyViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.mTitle.setText(list.get(position).title);
        holder.mPrice.setText("¥"+list.get(position).bargainPrice);
        holder.mNumControl.setGoods_storage(10);
        if(list.get(position).selected==0){
            holder.mChildSelected.setChecked(false);
        }else{
            holder.mChildSelected.setChecked(true);
        }

        TextView tvAmount = holder.mNumControl.findViewById(R.id.tvAmount);
        tvAmount.setText(list.get(position).num+"");

        holder.mNumControl.setListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                String updateurl = Api.UPDATE_CART_API + "?uid=" + uid + "&sellerid=" + list.get(position).sellerid + "&pid=" + list.get(position).pid + "&selected=" + list.get(position).selected + "&num=" + amount;
                System.out.println("**点击增加商品** 增加数量="+amount+"****" + updateurl);

                updateCart(updateurl);
                mChild.onProductChangeListener(amount,position);
            }
        });


        String images = list.get(position).images;
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.mImg);
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

        //长按监听事件
        if(mOnItemlongClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemlongClickListener.onitemLongClick(view,layoutPosition);
                    return false;

                }
            });
        }

        //设置商品选中时候的回调
        holder.mChildSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mChild!=null){


                           if(holder.mChildSelected.isChecked()){
                               list.get(position).selected=1;
                           }else{
                               list.get(position).selected=0;
                           }



                    int goodsNum = holder.mNumControl.getGoodsNum();
                    mChild.OnCheckChildListener(goodsNum,position);
                }

            }
        });



    }

    /**
     * 更新购物车
     *
     * @param url
     */
    private void updateCart(String url) {
        RequestrPresenter presenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    System.out.println("更新购物车==" + string);
                    try {
                        JSONObject obect = new JSONObject(string);
                        String code = obect.getString("code");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFail(Call call, IOException e) {

            }
        });


        presenter.requestData(url);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AmountView mNumControl;
        public ImageView mImg;
        public TextView mTitle;
        public TextView mPrice;
        public CheckBox mChildSelected;


        public MyViewHolder(View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.iv_child_item_img);
            mTitle = itemView.findViewById(R.id.tv_child_item_title);
            mPrice = itemView.findViewById(R.id.tv_child_item_price);
            mNumControl  = itemView.findViewById(R.id.amountView);
            mChildSelected = itemView.findViewById(R.id.cb_num_selected);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface  OnItemlongClickListener{
        void onitemLongClick(View view,int position);
    }





    private onChildcheck mChild;

    public void setChildcheck(onChildcheck mChild) {
        this.mChild = mChild;
    }

    public interface onChildcheck {
        //回调函数 将店铺商品的checkbox的点击变化事件进行回调
         void OnCheckChildListener(int num,int childpostion);
        //加减商品的回调
        void onProductChangeListener(int num,int childposition);
    }


}
