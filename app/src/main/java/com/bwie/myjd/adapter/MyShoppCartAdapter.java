package com.bwie.myjd.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwie.myjd.R;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.bean.CheckBoxBean;
import com.bwie.myjd.jsonbean.ShoppCartBean;
import com.bwie.myjd.presenter.RequestrPresenter;
import com.bwie.myjd.toast.ToastShow;
import com.bwie.myjd.view.OkRequestView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Time:2017/9/29 20:08
 * Author:王才文
 * Description:
 */

public class MyShoppCartAdapter extends RecyclerView.Adapter<MyShoppCartAdapter.MyViewHolder> {

    private Context context;
    private List<ShoppCartBean.DataBean> list;
    private List<CheckBoxBean> listCBparent;
    private OnItemClickListener mOnItemClickListener;
    private final LayoutInflater from;
    private int type=0;
    private MyShoppCartChildAdapter adapter;

    public MyShoppCartAdapter(Context context, List<ShoppCartBean.DataBean> list,List<CheckBoxBean> listCBparent) {
        this.listCBparent=listCBparent;
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

        View view = View.inflate(context,R.layout.shopping_cart_item,null);
            holder = new MyViewHolder(view);



        return holder;
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        holder.rv_list.setLayoutManager(layoutManager);
        holder.tv_name.setText(list.get(position).sellerName);
        holder.tv_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗





        final List<ShoppCartBean.DataBean.ListBean> listbean = this.list.get(position).list;
        for (ShoppCartBean.DataBean.ListBean bean : listbean) {
            boolean flag=true;
            if(bean.selected==0){
               flag=false;
            }
            holder.item_select.setChecked(flag);

        }


        adapter = new MyShoppCartChildAdapter(listbean,context);
        holder.rv_list.setAdapter(adapter);

        adapter.setOnItemlongClickListener(new MyShoppCartChildAdapter.OnItemlongClickListener() {
            @Override
            public void onitemLongClick(View view, int cposition) {

                if(list!=null && list.size()>0){
                    deleteData(position,cposition);//网络状态改变
                }

                list.get(position).list.remove(cposition);
                list.remove(position);
                adapter.notifyDataSetChanged();
                notifyDataSetChanged();
                ToastShow.showToast(context,"删除商品成功");
            }
        });

        holder.item_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //将店铺的checkbox的点击事件进行回调
                if(oncheck!=null){
                    for (ShoppCartBean.DataBean.ListBean bean : listbean) {
                        if(holder.item_select.isChecked()){
                            bean.selected=1;
                        }else{
                            bean.selected=0;
                        }
                    }

                    adapter.notifyDataSetChanged();
                    //传入店铺的索引
                    oncheck.OnCheckParentListener(position);

                }

            }
        });


        //商品选择的回调接口
        adapter.setChildcheck(new MyShoppCartChildAdapter.onChildcheck() {
            @Override
            public void OnCheckChildListener(int num, int childpostion) {
                //将店铺商品的checkbox的点击变化事件进行回调
                if (oncheck!=null){
                    //传入店铺索引,商品索引
                    oncheck.OnCheckChildListener(num,position,childpostion);
                    boolean flag=true;
                    for (ShoppCartBean.DataBean.ListBean bean : listbean) {
                        if(bean.selected==0){
                            flag=false;
                        }

                    }
                    holder.item_select.setChecked(flag);


                }
            }

            @Override//
            public void onProductChangeListener(int num, int childposition) {
                oncheck.onProductChangeListener(num,childposition,position);
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

    private void deleteData(int position,int cposition) {
        RequestrPresenter presenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    System.out.println("**删除商品**"+string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFail(Call call, IOException e) {

            }
        });

        presenter.requestData(Api.DELETE_PRODUCT+"?uid=169&pid="+list.get(position).list.get(cposition).pid);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        RecyclerView  rv_list;
        CheckBox item_select;
        public MyViewHolder(View itemView) {
            super(itemView);
            rv_list = itemView.findViewById(R.id.rv_item_list);
            tv_name = itemView.findViewById(R.id.tv_item_cart_shangjia_name);
            item_select = itemView.findViewById(R.id.cb_item_selected);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }




    private onParentcheck oncheck;

    public void setCallBack(onParentcheck oncheck) {
        this.oncheck = oncheck;
    }

    public interface onParentcheck{

        //回调函数 将店铺的checkbox的点击变化事件进行回调
         void OnCheckParentListener(int position);
        //回调函数 将店铺商品的checkbox的点击变化事件进行回调
         void OnCheckChildListener(int num,int parentposition,int chaildposition);

        void onProductChangeListener(int num,int parentposition,int chaildposition);

    }
}
