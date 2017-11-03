package com.bwie.myjd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.myjd.ChildClassifyListActivity;
import com.bwie.myjd.R;
import com.bwie.myjd.jsonbean.ChildClassify;

import java.util.List;

/**
 * Time:2017/10/4 15:44
 * Author:王才文
 * Description:
 */

public class MyClassifyDetailsAdapter extends RecyclerView.Adapter<MyClassifyDetailsAdapter.MyViewHolder>{

        private List<ChildClassify.DataBean> list;
        private Context context;
    private final LayoutInflater from;
    private MyClassifyDetailsChildAdapter adapter;

    public MyClassifyDetailsAdapter(List<ChildClassify.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        from = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = from.inflate(R.layout.classify_details_rv_item, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.mTvDetails.setText(list.get(position).name);
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.mRecyclerView.setLayoutManager(layout);
        for (final ChildClassify.DataBean dataBean : list) {
            if((list.get(position).pcid+"").equals(dataBean.pcid+"")){

                List<ChildClassify.DataBean.ListBean> listBean = dataBean.list;

                adapter = new MyClassifyDetailsChildAdapter(listBean,context);
                holder.mRecyclerView.setAdapter(adapter);



                //item的点击监听事件
                adapter.setOnItemClickListener(new MyClassifyDetailsChildAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //ToastShow.showToast(context,dataBean.list.get(position).name);
                        Intent intent = new Intent(context, ChildClassifyListActivity.class);
                        intent.putExtra("pscid",dataBean.list.get(position).pscid);
                        intent.putExtra("flag","0");
                        context.startActivity(intent);
                    }
                });
            }
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView mRecyclerView;
        public TextView mTvDetails;

        public MyViewHolder(View itemView) {
            super(itemView);
           mTvDetails = itemView.findViewById(R.id.tv_details_classify_item);
            mRecyclerView = itemView.findViewById(R.id.rv_child_classify_item);
        }
    }
}
