package com.bwie.myjd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bwie.myjd.api.Api;
import com.bwie.myjd.presenter.RegisterRequestrPresenter;
import com.bwie.myjd.toast.ToastShow;
import com.bwie.myjd.view.RegisterView;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    @InjectView(R.id.et_register_mobile)
    EditText etRegisterMobile;
    @InjectView(R.id.et_register_paw)
    EditText etRegisterPaw;
    @InjectView(R.id.bt_register)
    Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        initView();
    }

    private void initView() {
    btRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_register:
                RegisterRequestrPresenter register = new RegisterRequestrPresenter(new RegisterView() {
                    @Override
                    public void requestSuccess(Call call, Response response) {

                    }

                    @Override
                    public void requestFail(Call call, IOException e) {

                    }

                    @Override
                    public void registerSuccess(final String msg) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastShow.showToast(RegisterActivity.this,msg+",请登录~");
                                finish();
                            }
                        });
                    }

                    @Override
                    public void registerFail(final String msg) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastShow.showToast(RegisterActivity.this,msg);
                            }
                        });
                    }
                });

                register.registerData(Api.REGISTER_API,etRegisterMobile.getText().toString().trim(),etRegisterPaw.getText().toString().trim());
                break;
        }
    }
}
