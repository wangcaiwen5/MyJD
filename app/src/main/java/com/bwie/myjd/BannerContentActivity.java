package com.bwie.myjd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BannerContentActivity extends AppCompatActivity {

    @InjectView(R.id.wv_banner_url)
    WebView wvBannerUrl;
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_content);
        ButterKnife.inject(this);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);




        initWebView();
        initWebViewData();
    }

    private void initWebViewData() {

        wvBannerUrl.loadUrl(getIntent().getStringExtra("url"));
    }

    private void initWebView() {
        wvBannerUrl.getSettings().setJavaScriptEnabled(true);
        wvBannerUrl.setWebViewClient(new WebViewClient());
    }
}
