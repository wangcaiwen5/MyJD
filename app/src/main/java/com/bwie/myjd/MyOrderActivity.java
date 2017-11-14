package com.bwie.myjd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bwie.myjd.adapter.MyOrderListAdapter;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.jsonbean.MyOrder;
import com.bwie.myjd.toast.ToastShow;
import com.example.myokhttp.CallbackInterface;
import com.example.myokhttp.Method;
import com.example.myokhttp.OkRequestUtils;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

public class MyOrderActivity extends AppCompatActivity {

    @InjectView(R.id.rv_myorderlist)
    RecyclerView rvMyorderlist;
    @InjectView(R.id.tv_nextPage)
    TextView tvNextPage;
    @InjectView(R.id.tv_upPage)
    TextView tvUpPage;
    private MyOrderListAdapter adapter;
    private int page = 1;
    private List<MyOrder.DataBean> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.inject(this);

        System.out.println("oncreate");

        initView();
        initData();
    }



    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart");
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        rvMyorderlist.setLayoutManager(new LinearLayoutManager(this));
        tvNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page++;
                if (page>3){
                    page=3;
                    ToastShow.showToast(MyOrderActivity.this,"我是有底线的~~");
                    return;
                }

                    list.clear();
                    adapter = null;
                    initData();


            }
        });
        tvUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page--;
                if(page<1){
                    page=1;
                    ToastShow.showToast(MyOrderActivity.this,"我是有底线的~~");
                    return;
                }

                list.clear();
                adapter = null;
                initData();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", 169 + "");
        map.put("page", page + "");
        OkRequestUtils.call(Api.ORDERE_LIST, Method.POST, map);
        OkRequestUtils.getInstance(this).setCallback(new CallbackInterface() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson = new Gson();
                    MyOrder myOrder = gson.fromJson(string, MyOrder.class);
                    final List<MyOrder.DataBean> data = myOrder.data;

                    if (data.size() > 0 && data != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter == null) {
                                    list.addAll(data);
                                    adapter = new MyOrderListAdapter(MyOrderActivity.this, list);
                                    rvMyorderlist.setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
