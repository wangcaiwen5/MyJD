package com.bwie.myjd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.jsonbean.GetInfo;
import com.bwie.myjd.presenter.RequestrPresenter;
import com.bwie.myjd.view.OkRequestView;
import com.bwie.uploadpicture.view.CircleImageView;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {


    @InjectView(R.id.iv_setting_img)
    CircleImageView ivSettingImg;
    @InjectView(R.id.tv_setting_username)
    TextView tvSettingUsername;
    @InjectView(R.id.setting_personal)
    RelativeLayout settingPersonal;
    @InjectView(R.id.bt_drop_out)
    Button btDropOut;
    private SharedPreferences isLogin;

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);

        initSp();
        initSpData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        SharedPreferences isLogin = getSharedPreferences("isLogin", MODE_PRIVATE);
        int uid = isLogin.getInt("uid", -1);
        String url = Api.GETINFO+"?uid="+uid;
        RequestrPresenter presenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    System.out.println("用户信息"+string);
                    Gson gson = new Gson();
                    GetInfo getInfo = gson.fromJson(string, GetInfo.class);
                    final GetInfo.DataBean data = getInfo.data;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Glide.get(SettingActivity.this).clearMemory();
                            // 清理内存中的缓存。

                           // Glide.get(SettingActivity.this).clearDiskCache();
                            //清理硬盘中的缓存。
                            Glide.with(SettingActivity.this).load(data.icon).into(ivSettingImg);
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

    private void initSpData() {
        tvSettingUsername.setText(isLogin.getString("username", null));
    }

    private void initSp() {

        isLogin = getSharedPreferences("isLogin", MODE_PRIVATE);

    }

    private void initView() {
        settingPersonal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_personal:
                if (isLogin.getBoolean("islogin", false)) {
                    Intent intent = new Intent(SettingActivity.this, PersonalinformationActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }
}
