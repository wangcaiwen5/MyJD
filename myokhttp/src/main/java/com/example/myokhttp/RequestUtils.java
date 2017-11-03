package com.example.myokhttp;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

public class RequestUtils {

    private  CallbackInterface callbackInterface;


    /**
     * get方法
     * ?前面的地址
     * 请求方法
     * 传网址后面的参数
     * @param url
     */
     public void call(String url, Method m, Map<String,String> params)
     {

          Request request=null;

         OkHttpClient okHttpClient=new OkHttpClient.Builder().sslSocketFactory(createSSLSocketFactory()).hostnameVerifier(new TrustAllHostnameVerifier() )
                // .addInterceptor(new LogInterceptor())
                 .connectTimeout(10, TimeUnit.SECONDS)
                 .readTimeout(10, TimeUnit.SECONDS)
                 .writeTimeout(10, TimeUnit.SECONDS)
                 .retryOnConnectionFailure(false)
                 .build();;

         if(m==Method.GET)
         {

             String urls=url+"?";
             //拼接请求参数
             for (Map.Entry<String, String> stringStringEntry : params.entrySet())
             {

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



    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory SSLFactory=null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            SSLFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return SSLFactory;

    }

    public static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    //暴露接口
    public void setCallback(CallbackInterface callbackInterface) {
        this.callbackInterface = callbackInterface;
    }
}
