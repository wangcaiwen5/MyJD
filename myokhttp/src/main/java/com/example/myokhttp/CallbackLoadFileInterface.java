package com.example.myokhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Time:2017/9/13 15:57
 * Author:wen
 * Description:
 */
public interface CallbackLoadFileInterface {
     void onFailure(Call call, IOException e);
     void onResponse(Call call, Response response);
}
