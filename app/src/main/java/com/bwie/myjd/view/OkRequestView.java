package com.bwie.myjd.view;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Time:2017/10/9 17:12
 * Author:王才文
 * Description:
 */

public interface OkRequestView {
    void requestSuccess(Call call, Response response);//请求成功
    void requestFail(Call call, IOException e);//请求失败


}
