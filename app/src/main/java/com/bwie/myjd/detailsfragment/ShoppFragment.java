package com.bwie.myjd.detailsfragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.myjd.R;
import com.bwie.myjd.adapter.DetailsPageAdapter;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.jsonbean.DetailsBean;
import com.bwie.myjd.myview.MyScrollView;
import com.bwie.myjd.myview.MyViewPager;
import com.bwie.myjd.presenter.RequestrPresenter;
import com.bwie.myjd.toast.ToastShow;
import com.bwie.myjd.view.OkRequestView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Time:2017/10/16 20:51
 * Author:王才文
 * Description:
 */

public class ShoppFragment extends Fragment implements View.OnClickListener {


    @InjectView(R.id.vp_details_page)
    MyViewPager vpDetailsPage;
    @InjectView(R.id.tv_current_img)
    TextView tvCurrentImg;
    @InjectView(R.id.tv_sum_img)
    TextView tvSumImg;
    @InjectView(R.id.tv_details_msg)
    TextView tvDetailsMsg;
    @InjectView(R.id.tv_details_price)
    TextView tvDetailsPrice;
    @InjectView(R.id.tv_subhead)
    TextView tvSubhead;
    @InjectView(R.id.iv_dz_img)
    ImageView ivDzImg;
    @InjectView(R.id.tv_dz_name)
    TextView tvDzName;
    @InjectView(R.id.tv_gz_num)
    TextView tvGzNum;
    @InjectView(R.id.tv_product_num)
    TextView tvProductNum;
    @InjectView(R.id.myshopp_scrollview)
    MyScrollView myshoppScrollview;
    @InjectView(R.id.tv_dz_description)
    TextView tvDzDescription;
    @InjectView(R.id.tv_dz_dt)
    TextView tvDzDt;
    @InjectView(R.id.iv_webview)
    WebView ivWebview;
    @InjectView(R.id.bt_add_shopping_cart)
    Button btAddShoppingCart;
    @InjectView(R.id.iv_shopping_cart)
    ImageView ivShoppingCart;

    private DetailsPageAdapter adapter;
    private String pid;
    private View view;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int sumnum = msg.arg1;
            tvCurrentImg.setText(sumnum + "");

        }
    };
    private int height;
    private int uid;
    private int sellerid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = View.inflate(getActivity(), R.layout.shopp_fragment, null);

        }

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }


        ButterKnife.inject(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSp();
        initView();
        getCid();
        initData();
        initpage();
    }

    private void initSp() {
        SharedPreferences isLogin = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        uid = isLogin.getInt("uid", -1);
    }

    private void initView() {
        btAddShoppingCart.setOnClickListener(this);
        WindowManager wm = getActivity().getWindowManager();
        height = wm.getDefaultDisplay().getHeight();
        System.out.println("屏幕高度==" + height);
        ivWebview.setWebViewClient(new WebViewClient());
        ivWebview.getSettings().setJavaScriptEnabled(true);

    }

    private void initpage() {

        vpDetailsPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Message msg = new Message();
                msg.arg1 = position + 1;
                handler.sendMessage(msg);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void getCid() {
        Bundle b = getArguments();
        if (b != null) {
            pid = b.getString("pid");
            System.out.println("shopping_cid==" + pid);
        }

    }

    /**
     * 网络请求数据
     */
    private void initData() {


        String url = Api.PRODUCT_DETAIL + "?" + "pid=" + pid;
        RequestrPresenter presenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    System.out.println("商品详情string" + string);
                    Gson gson = new Gson();
                    DetailsBean detailsBean = gson.fromJson(string, DetailsBean.class);
                    DetailsBean.DataBean data = detailsBean.data;
                    DetailsBean.SellerBean seller = detailsBean.seller;
                    //店铺部分
                    final String description = seller.description;
                    final String icon = seller.icon;
                    final String name = seller.name;
                    final int productNums = seller.productNums;
                    double score = seller.score;
                    sellerid = seller.sellerid;
                    //详情部分
                    String images = data.images;
                    final String subhead = data.subhead;
                    final String title = data.title;
                    final double bargainPrice = data.bargainPrice;
                    final String detailUrl = data.detailUrl;
                    System.out.println("图片url==" + images);


                    final String[] split = images.split("\\|");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //店铺设置
                            Glide.with(getActivity()).load(icon).into(ivDzImg);
                            tvDzName.setText(name);
                            tvDzDescription.setText(description);
                            tvProductNum.setText(productNums + "");
                            tvDzDt.setText(sellerid + " ");
                            //详情设置
                            tvSumImg.setText("/" + split.length);
                            tvDetailsMsg.setText(title);
                            ivWebview.loadUrl(detailUrl);
                            tvDetailsPrice.setText("¥" + bargainPrice);
                            tvDetailsPrice.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                            tvSubhead.setText(subhead);

                            myshoppScrollview.setOnScrollListener(new MyScrollView.OnScrollListener() {
                                @Override
                                public void onScroll(int scrollY) {
                                    // if(scrollY>){}
                                }
                            });

                            if (adapter == null) {
                                adapter = new DetailsPageAdapter(split, getActivity());
                                vpDetailsPage.setAdapter(adapter);
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFail(Call call, IOException e) {

            }
        });
        presenter.requestData(url);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_shopping_cart://添加到购物车
                // 将一个TextView沿垂直方向先从原大小（1f）放大到5倍大小（5f），然后再变回原大小。
                ObjectAnimator anim = ObjectAnimator.ofFloat(ivShoppingCart, "scaleY", 1f, 2f, 1f);
                anim.setDuration(2000);
                anim.start();
                initAddData();
                break;
        }
    }

    private void initAddData() {
        String url = Api.ADD_SHOPPING_CAR_API + "?uid=" + uid + "&&pid=" + pid + "&&sellerid=" + sellerid;
        System.out.println("****添加购物车****"+url);

        RequestrPresenter presenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    final String msg = jsonObject.getString("msg");
                    System.out.println("");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastShow.showToast(getActivity(), msg);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFail(Call call, IOException e) {

            }
        });

        presenter.requestData(url);

    }
}