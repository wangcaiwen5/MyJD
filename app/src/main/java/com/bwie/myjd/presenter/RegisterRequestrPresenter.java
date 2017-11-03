package com.bwie.myjd.presenter;

import com.bwie.myjd.view.RegisterView;
import com.example.myokhttp.CallbackInterface;
import com.example.myokhttp.Method;
import com.example.myokhttp.RequestUtils;

import org.json.JSONObject;

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

public class RegisterRequestrPresenter implements CallbackInterface{

    private RegisterView requestView;
    private RequestUtils utils;

    public RegisterRequestrPresenter(RegisterView requestView) {
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
        try {
            String string = response.body().string();
            JSONObject object = new JSONObject(string);
            String code = object.getString("code");
            String msg = object.getString("msg");
            System.out.println("msg=="+msg+"=="+string);
            if(code.equals("0")){
                requestView.registerSuccess(msg);
            }else{
                requestView.registerFail(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
