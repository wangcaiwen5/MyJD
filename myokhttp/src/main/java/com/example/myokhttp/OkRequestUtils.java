package com.example.myokhttp;

import android.content.Context;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Time:2017/9/13 15:28
 * Author:
 * Description:okhttp同步异步方法
 */

public class OkRequestUtils {

    private Context mContext;
    //单例模式
    private static OkRequestUtils OkRequestInstance ;

    /**
     * 私有构思方法
     * context
     */
    private OkRequestUtils(Context context){

        this.mContext=context;
    }

    /**
     * 对外暴露的实例
     */
    public static OkRequestUtils getInstance(Context context){

        if(OkRequestInstance==null){
            synchronized (OkRequestUtils.class){
                if(OkRequestInstance==null){
                    OkRequestInstance = new OkRequestUtils(context);
                }
            }
        }

        return OkRequestInstance;

    }

    private static   CallbackInterface callbackInterface;


    /**
     * get方法
     * ?前面的地址
     * 请求方法
     * 传网址后面的参数
     * @param url
     */
     public static  void call(String url, Method m, Map<String,String> params)
     {

          Request request=null;

         OkHttpClient okHttpClient=new OkHttpClient();
         if(m==Method.GET)
         {
             //http://v.juhe.cn/toutiao/index?
             String urls=url+"?";
             //拼接请求参数
             for (Map.Entry<String, String> stringStringEntry : params.entrySet())
             {
                   //key=c1885686ef47f19bcb45e39c4447e040
                    urls+=stringStringEntry.getKey()+"="+stringStringEntry.getValue()+"&&";
             }

             request=new Request.Builder()
                     .get()
                     .url(urls)
                     .build();
         }
         /**
          * post方法
          */
         else if(m==Method.POST)
         {

             FormBody.Builder fBuilder=new FormBody.Builder();
             //params.entrySet().for
             for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
                 fBuilder.add(stringStringEntry.getKey(),stringStringEntry.getValue());
             }
             RequestBody build = fBuilder.build();
             request = new Request.Builder()
                     .post(build)
                     .url(url)
                     .build();
         }


         //执行异步请求
         Call call = okHttpClient.newCall(request);
         call.enqueue(new Callback() {
             @Override
             public void onFailure(Call call, IOException e) {
                 if(callbackInterface!=null){
                     //失败
                     callbackInterface.onFailure(call,e);
                 }

             }

             @Override
             public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    if(callbackInterface!=null){
                        //成功
                        callbackInterface.onResponse(call,response);
                    }

                }
             }
         });

     }

     //让他在其他方式可以调用
    public void setCallback(CallbackInterface callbackInterface) {
        this.callbackInterface = callbackInterface;
    }


}
