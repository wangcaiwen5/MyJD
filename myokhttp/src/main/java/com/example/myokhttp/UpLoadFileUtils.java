package com.example.myokhttp;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Time:2017/10/12 22:06
 * Author:王才文
 * Description:
 */

public class UpLoadFileUtils {

    private Context mContext;
    //单例模式
    private static UpLoadFileUtils loadFileInstance ;

    /**
     * 私有构思方法
     * context
     */
private UpLoadFileUtils(Context context){

    this.mContext=context;
}

/**
 * 对外暴露的实例
 */
public static UpLoadFileUtils getLoadFileInstance(Context context){

    if(loadFileInstance==null){
        synchronized (UpLoadFileUtils.class){
            if(loadFileInstance==null){
                loadFileInstance = new UpLoadFileUtils(context);
            }
        }
    }

    return loadFileInstance;

}

public void loadFile(String url, Map<String,Object> params, final CallbackLoadFileInterface callbackInterface){

    MediaType mediaType = MediaType.parse("application/octet-stream");
    OkHttpClient client = new OkHttpClient();
    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

    for (Map.Entry<String, Object> entry : params.entrySet()) {
        if (entry.getValue() instanceof File){
            File file = (File) entry.getValue();
            builder.addFormDataPart(entry.getKey(),file.getName(), RequestBody.create(mediaType,file));
        }else{
            builder.addFormDataPart(entry.getKey(),entry.getValue().toString());
        }

    }

    MultipartBody multipartBody = builder.build();
    Request request = new Request.Builder()
            .url(url)
            .post(multipartBody)
            .build();

    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            callbackInterface.onFailure(call,e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            System.out.println("上传"+response.body().string());
            if(response.isSuccessful()){
                InputStream inputStream = response.body().byteStream();
                callbackInterface.onResponse(call,response);
            }
        }
    });



}

}
