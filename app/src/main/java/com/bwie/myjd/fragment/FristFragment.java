package com.bwie.myjd.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.myjd.BannerContentActivity;
import com.bwie.myjd.DetailsActivity;
import com.bwie.myjd.R;
import com.bwie.myjd.SearchActivity;
import com.bwie.myjd.adapter.ClassifyPagerAdapter;
import com.bwie.myjd.adapter.MyRecommendAdapter;
import com.bwie.myjd.adapter.MySecondKillAdapter;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.itemmargin.SpacesItemDecoration;
import com.bwie.myjd.jsonbean.Classfiy;
import com.bwie.myjd.jsonbean.GetAd;
import com.bwie.myjd.myview.MyScrollView;
import com.bwie.myjd.presenter.RequestrPresenter;
import com.bwie.myjd.toast.ToastShow;
import com.bwie.myjd.view.OkRequestView;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.wen.countdowntimer.view.SecondDownTimerView;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Time:2017/9/29 10:26
 * Author:王才文
 * Description:
 */

public class FristFragment extends Fragment implements View.OnClickListener,MyScrollView.OnScrollListener {


    @InjectView(R.id.x_banner)
    XBanner xBanner;
    @InjectView(R.id.vp_page)
    ViewPager vpPage;
    @InjectView(R.id.ll_dot)
    LinearLayout llDot;
    @InjectView(R.id.iv_clock)
    ImageView ivClock;
    @InjectView(R.id.tv)
    TextView tv;
    @InjectView(R.id.tv2)
    TextView tv2;
    @InjectView(R.id.down_time)
    SecondDownTimerView downTime;
    @InjectView(R.id.rv_listview)
    RecyclerView rvListview;
    @InjectView(R.id.rv_recommend_list)
    RecyclerView rvRecommendList;
    @InjectView(R.id.iv_frist_scan_code)
    ImageView ivFristScanCode;
    @InjectView(R.id.tv_search_text)
    TextView tvSearchText;
    @InjectView(R.id.ll_scan_code)
    LinearLayout llScanCode;
    @InjectView(R.id.iv_received_msg)
    ImageView ivReceivedMsg;
    @InjectView(R.id.tv_received_msg_text)
    TextView tvReceivedMsgText;
    @InjectView(R.id.ll_message)
    PercentRelativeLayout llMessage;
    @InjectView(R.id.rl_search)
    PercentRelativeLayout rlSearch;
    @InjectView(R.id.mtoolbar)
    Toolbar mtoolbar;
    @InjectView(R.id.sv_scrollview)
    MyScrollView svScrollview;
    private int mDistanceY;
    private List<List<Classfiy.DataBean>> sumlist = new ArrayList<>();


    private View fristview;
    private ClassifyPagerAdapter classifyPagerAdapter;
    private MySecondKillAdapter secondKilladapter;
    private List<GetAd.DataBean> bannerdata;
    private List<GetAd.MiaoshaBean.ListBeanX> listSecond = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (fristview == null) {
            fristview = View.inflate(getActivity(), R.layout.frist_fragment, null);

        }

        ViewGroup parent = (ViewGroup) fristview.getParent();
        if (parent != null) {
            parent.removeView(fristview);
        }

        ButterKnife.inject(this, fristview);
        initView();

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        return fristview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initDot();
        initBanner();//轮播图
        initFristClassfiy();//分类
        initSecond();//秒杀
        initRecommend();//推荐
        RecyclerViewsetAlpha();
        initDownTime();

    }

    /**
     * 推荐
     */
    private void initRecommend() {
        RequestrPresenter request = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    System.out.println("推荐=="+string);
                    Gson gson = new Gson();
                    GetAd getAd = gson.fromJson(string, GetAd.class);

                    GetAd.TuijianBean tuijian = getAd.tuijian;
                    final List<GetAd.TuijianBean.ListBean> list = tuijian.list;
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (rvRecommendList != null && list != null){
                                    MyRecommendAdapter adapter =new MyRecommendAdapter(getActivity(), list);
                                    rvRecommendList.setAdapter(adapter);
                                    adapter.setOnItemClickListener(new MyRecommendAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            ToastShow.showToast(getActivity(),list.get(position).pid+"");
                                            Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
                                            intent.putExtra("pid",list.get(position).pid);
                                            System.out.println("pid传值"+list.get(position).pid);
                                            getActivity().startActivity(intent);
                                        }
                                    });
                                }

                            }
                        });
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFail(Call call, IOException e) {

            }
        });

        request.requestData(Api.GETAD_API);



    }

    private void initDownTime() {
        downTime.setDownTime(140000000);
    }


    /**
     * 初始化小圆点
     */
    private void initDot() {
        List<ImageView> listTv = new ArrayList<>();
        for (int i = 0; i < sumlist.size(); i++) {
            ImageView iv = new ImageView(getActivity());
            if (i == 0) {
                iv.setImageResource(R.drawable.dot_select);
            } else {
                iv.setImageResource(R.drawable.dot_normal);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(5, 5, 5, 5);
            listTv.add(iv);
            llDot.addView(iv, params);
        }

    }

    /**
     * 分类
     */
    private void initFristClassfiy() {


        RequestrPresenter requestrPresenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson = new Gson();
                    Classfiy classfiy = gson.fromJson(string, Classfiy.class);
                    final List<Classfiy.DataBean> classfiylist = classfiy.data;
                    List<Classfiy.DataBean> alist = classfiylist.subList(0, 10);
                    List<Classfiy.DataBean> blist = classfiylist.subList(10, classfiylist.size());
                    sumlist.clear();
                    sumlist.add(alist);
                    sumlist.add(blist);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (classifyPagerAdapter == null) {
                                classifyPagerAdapter = new ClassifyPagerAdapter(sumlist, getActivity());
                                vpPage.setAdapter(classifyPagerAdapter);
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

        requestrPresenter.requestData(Api.CLASSIFY_API);

    }

    private void initBanner() {

        RequestrPresenter requestrPresenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    if (string.length() > 10) {
                        System.out.println("轮播" + string);
                        Gson gson = new Gson();
                        GetAd getAd = gson.fromJson(string, GetAd.class);
                        bannerdata = getAd.data;


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //添加头布局
                                if (bannerdata != null)
                                    initBanner(bannerdata);
                            }
                        });

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFail(Call call, IOException e) {

            }
        });

        requestrPresenter.requestData(Api.GETAD_API);


    }

    /**
     * 首页分类
     */
    private void initSecond() {
        RequestrPresenter requestrPresenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    if (string.length() > 100) {
                        System.out.println("轮播图" + string);
                        Gson gson = new Gson();
                        GetAd getAd = gson.fromJson(string, GetAd.class);

                        final GetAd.MiaoshaBean miaosha = getAd.miaosha;

                        listSecond = miaosha.list;


                        if (listSecond.size() > 0 && listSecond != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initSecondKill(listSecond);
                                }
                            });
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFail(Call call, IOException e) {

            }
        });

        requestrPresenter.requestData(Api.GETAD_API);


    }

    /**
     * 秒杀
     *
     * @param listSecond
     */
    private void initSecondKill(List<GetAd.MiaoshaBean.ListBeanX> listSecond) {
        if (secondKilladapter == null) {
            secondKilladapter = new MySecondKillAdapter(getActivity(), listSecond);
            if (rvListview != null)
                rvListview.setAdapter(secondKilladapter);
        }
    }


    private void initBanner(final List<GetAd.DataBean> bannerBeen) {


        //添加轮播头布局

        final List<String> liststrs = new ArrayList<>();
        final List<String> listimgs = new ArrayList<>();
        for (int i = 0; i < bannerBeen.size(); i++) {
            listimgs.add(bannerBeen.get(i).icon);
            liststrs.add(bannerBeen.get(i).title);
        }
        if (listimgs.size() > 0 && listimgs != null && liststrs.size() > 0 && liststrs != null && xBanner != null) {
            xBanner.setData(listimgs, liststrs);

            // XBanner适配数据
            xBanner.setmAdapter(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(getActivity()).load(listimgs.get(position)).into((ImageView) view);
                }
            });

            // XBanner中某一项的点击事件
            xBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, int position) {
                    for (int i = 0; i < bannerBeen.size(); i++) {
                        if (position == i) {
                            Intent intent = new Intent(getActivity(), BannerContentActivity.class);
                            intent.putExtra("url", bannerBeen.get(i).url);
                            startActivity(intent);
                        }
                    }
                }
            });
        }


    }


    /**
     * 初始化View
     */
    private void initView() {
        rlSearch.setOnClickListener(this);
        mtoolbar.setOnClickListener(this);
        //设置瀑布流模式推荐
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        rvRecommendList.setLayoutManager(layout);


        SpacesItemDecoration decoration = new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.y3), getResources().getDimensionPixelSize(R.dimen.y3), true);
        rvRecommendList.addItemDecoration(decoration);
        //秒杀设置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvListview.setLayoutManager(linearLayoutManager);
        // rvListview.setLayoutManager(layout);
        //设置item之间的间隔


    }

    /**
     * 设置渐变
     */
    private void RecyclerViewsetAlpha() {
        svScrollview.setOnScrollListener(this);
        mtoolbar.setOnClickListener(this);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        xBanner.startAutoPlay();
        downTime.startDownTimer();
    }

    @Override
    public void onStop() {
        super.onStop();

        xBanner.stopAutoPlay();
        downTime.cancelDownTimer();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     *  myscrollview的滑动监听
     */

    @Override
    public void onScroll(int scrollY) {
        //滑动的距离
        mDistanceY = scrollY;
        //toolbar的高度
        final int toolbarHeight = mtoolbar.getBottom();
        System.out.println("toolbarHeight==" + toolbarHeight);
        System.out.println("mDistanceY==" + mDistanceY);
        //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
        if (mDistanceY <= toolbarHeight) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            System.out.println("mDistanceY==" + mDistanceY);
            System.out.println("toolbarHeight==" + toolbarHeight);
            float scale = (float) mDistanceY / toolbarHeight;
            float alpha = scale * 255;
            mtoolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            tvSearchText.setTextColor(Color.WHITE);
            ivReceivedMsg.setImageResource(R.drawable.jd_message);
            ivFristScanCode.setImageResource(R.drawable.jd_scan_code);
            tvReceivedMsgText.setTextColor(Color.WHITE);
            rlSearch.setBackgroundResource(R.drawable.shape_edit_search);
        } else {
            //将标题栏的颜色设置为完全不透明状态
            //mtoolbar.setBackgroundResource(R.color.white_text_color);
            tvSearchText.setTextColor(Color.BLACK);
            tvReceivedMsgText.setTextColor(Color.BLACK);
            ivFristScanCode.setImageResource(R.drawable.black_scan_code);
            ivReceivedMsg.setImageResource(R.drawable.black_message);
            rlSearch.setBackgroundResource(R.drawable.gray_shape_edit_search);
        }

    }
}
