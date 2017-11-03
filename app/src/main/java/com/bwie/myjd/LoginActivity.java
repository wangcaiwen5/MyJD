package com.bwie.myjd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.myjd.api.Api;
import com.bwie.myjd.presenter.LoginRequestrPresenter;
import com.bwie.myjd.toast.ToastShow;
import com.bwie.myjd.view.LoginView;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @InjectView(R.id.et_name)
    EditText etName;
    @InjectView(R.id.et_paw)
    EditText etPaw;
    @InjectView(R.id.bt_login)
    Button btLogin;
    @InjectView(R.id.tv_register)
    TextView tvRegister;
    @InjectView(R.id.tv_updatePaw)
    TextView tvUpdatePaw;
    @InjectView(R.id.weixin)
    ImageView weixin;
    @InjectView(R.id.qq)
    ImageView qq;
    private SharedPreferences isLogin;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        spData();
        initView();
    }

    private void spData() {
        isLogin = getSharedPreferences("isLogin", MODE_PRIVATE);
        edit = isLogin.edit();
    }

    private void initView() {
    btLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                LoginRequestrPresenter login = new LoginRequestrPresenter(new LoginView() {
                    @Override
                    public void requestSuccess(Call call, Response response) {


                    }

                    @Override
                    public void requestFail(Call call, IOException e) {

                    }

                    @Override
                    public void loginSuccess(final String msg,int uid) {
                        System.out.println("uid=="+uid);
                        edit.putInt("uid",uid).commit();
                        edit.putBoolean("islogin", true).commit();
                        edit.putString("username",etName.getText().toString().trim()).commit();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ToastShow.showToast(LoginActivity.this,"登录成功");
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("flag",1);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void loginFail(final String msg) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastShow.showToast(LoginActivity.this,msg);
                            }
                        });
                    }
                });
                login.registerData(Api.LOGIN_API,etName.getText().toString().trim(), etPaw.getText().toString().trim());
                break;

            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
