package com.example.myokhttp;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
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

public class OkHttpUtils {

    private static Context mContext;
    //单例模式
    private static OkHttpUtils OkRequestInstance ;

    /**
     * 私有构思方法
     * context
     */
    private OkHttpUtils(Context context){

        this.mContext=context;
    }

    /**
     * 对外暴露的实例
     */
    public static OkHttpUtils getInstance(Context context){

        if(OkRequestInstance==null){
            synchronized (OkHttpUtils.class){
                if(OkRequestInstance==null){
                    OkRequestInstance = new OkHttpUtils(context);
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
     public static  void call(String url, Method m,HTTPS https, Map<String,String> params,File file)
     {

          Request request=null;
         OkHttpClient okHttpClient = setCard(https, file);
         if(m==Method.GET)
         {
        request=new Request.Builder()
                     .get()
                     .url(url)
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

    //证书验证
    public static OkHttpClient setCard(HTTPS httpsType, File fileInputStream){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
         if(httpsType==HTTPS.CURRENT_HTTPS && fileInputStream!=null){

             try {
                 CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                 KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                 keyStore.load(null);
                 String certificateAlias = Integer.toString(0);
                 keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(mContext.getAssets().open("kson_server.cer")));//拷贝好的证书
                 SSLContext sslContext = SSLContext.getInstance("TLS");
                 final TrustManagerFactory trustManagerFactory =
                         TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                 trustManagerFactory.init(keyStore);
                 sslContext.init
                         (
                                 null,
                                 trustManagerFactory.getTrustManagers(),
                                 new SecureRandom()
                         );
                 builder.sslSocketFactory(sslContext.getSocketFactory());
                 builder.addInterceptor(new LogInterceptor());
                 builder.hostnameVerifier(new HostnameVerifier() {
                     @Override
                     public boolean verify(String s, SSLSession sslSession) {
                         return true;
                     }
                 });
             } catch (Exception e) {
                 e.printStackTrace();
             }

         }else if (httpsType==HTTPS.ALL_HTTPS && fileInputStream==null){
             builder.sslSocketFactory(createSSLSocketFactory()).hostnameVerifier(new RequestUtils.TrustAllHostnameVerifier() )
                     // .addInterceptor(new LogInterceptor())
                     .connectTimeout(10, TimeUnit.SECONDS)
                     .readTimeout(10, TimeUnit.SECONDS)
                     .writeTimeout(10, TimeUnit.SECONDS)
                     .retryOnConnectionFailure(false)
                     .build();;


         }

        return builder.build();

    }


    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory SSLFactory=null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new RequestUtils.TrustAllCerts()}, new SecureRandom());

            SSLFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return SSLFactory;

    }

    private static class TrustAllCerts implements X509TrustManager {
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

}
