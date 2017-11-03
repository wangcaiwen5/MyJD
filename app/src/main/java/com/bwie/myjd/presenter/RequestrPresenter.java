package com.bwie.myjd.presenter;

import com.bwie.myjd.view.OkRequestView;
import com.example.myokhttp.CallbackInterface;
import com.example.myokhttp.Method;
import com.example.myokhttp.RequestUtils;

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

public class RequestrPresenter implements CallbackInterface{

    private OkRequestView requestView;
    private RequestUtils utils;

    public RequestrPresenter(OkRequestView requestView) {
        this.requestView = requestView;
        utils = new RequestUtils();
        utils.setCallback(this);

    }

    //开始请求
    public void requestData(String url){

        Map<String, String> map = new HashMap<>();
        utils.call(url, Method.POST,map);


    }


    @Override
    public void onFailure(Call call, IOException e) {
        requestView.requestFail(call,e);
    }

    @Override
    public void onResponse(Call call, Response response) {
        requestView.requestSuccess(call,response);
    }
}
