package com.bwie.myjd.presenter;

import com.bwie.myjd.jsonbean.LoginBean;
import com.bwie.myjd.view.LoginView;
import com.example.myokhttp.CallbackInterface;
import com.example.myokhttp.Method;
import com.example.myokhttp.RequestUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Time:2017/10/9 17:10
 * Author:王才文
 * Description:
 */

public class LoginRequestrPresenter implements CallbackInterface{

    private LoginView requestView;
    private RequestUtils utils;

    public LoginRequestrPresenter(LoginView requestView) {
        this.requestView = requestView;
        utils = new RequestUtils();
        utils.setCallback(this);

    }

    //开始请求
    public void registerData(String url,String mobile,String password){

        Map<String, String> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("password",password);
        utils.call(url, Method.POST,map);


    }


    @Override
    public void onFailure(Call call, IOException e) {
        requestView.requestFail(call,e);
    }

    @Override
    public void onResponse(Call call, Response response) {

        if (response.isSuccessful()){

            try {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(response.body().string(), LoginBean.class);
                String msg = loginBean.msg;
                String code = loginBean.code;



                if(code.equals("0")){
                    LoginBean.DataBean data = loginBean.data;
                    int uid = data.uid;
                    System.out.println("登录成功");
                    requestView.loginSuccess(msg,uid);

                }else{
                    requestView.loginFail(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
