package com.bwie.myjd.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.myjd.LoginActivity;
import com.bwie.myjd.MyOrderActivity;
import com.bwie.myjd.R;
import com.bwie.myjd.SettingActivity;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.jsonbean.GetInfo;
import com.bwie.myjd.presenter.RequestrPresenter;
import com.bwie.myjd.view.OkRequestView;
import com.bwie.uploadpicture.view.CircleImageView;
import com.google.gson.Gson;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Time:2017/9/29 10:26
 * Author:王才文
 * Description:
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.iv_head_img)
    CircleImageView ivHeadImg;
    @InjectView(R.id.shezhi_setting)
    ImageView shezhiSetting;
    @InjectView(R.id.pll)
    PercentLinearLayout pll;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.ll_my_order)
    LinearLayout llMyOrder;
    private View view;
    private SharedPreferences isLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = View.inflate(getActivity(), R.layout.my_fragment, null);

        }

        isLogin = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        int uid = isLogin.getInt("uid", -1);
        if (uid != -1) {
            String url = Api.GETINFO + "?uid=" + uid;
            RequestrPresenter presenter = new RequestrPresenter(new OkRequestView() {
                @Override
                public void requestSuccess(Call call, Response response) {
                    try {
                        String string = response.body().string();
                        System.out.println("用户信息" + string);
                        Gson gson = new Gson();
                        final GetInfo getInfo = gson.fromJson(string, GetInfo.class);
                        final GetInfo.DataBean data = getInfo.data;

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Glide.get(getActivity()).clearMemory();
                                // 清理内存中的缓存。

                                // Glide.get(getActivity()).clearDiskCache();
                                //清理硬盘中的缓存。
                                Glide.with(getActivity()).load(data.icon).into(ivHeadImg);
                            }
                        });
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

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initSp();
    }

    private void initSp() {

        tvName.setText(isLogin.getString("username", null));
    }

    private void initView() {
        llMyOrder.setOnClickListener(this);
        ivHeadImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head_img:
                if (isLogin.getBoolean("islogin", false)) {
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

                break;

            case R.id.shezhi_setting:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_my_order:
                Intent intent1 = new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
