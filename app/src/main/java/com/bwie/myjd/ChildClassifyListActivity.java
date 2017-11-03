package com.bwie.myjd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bwie.myjd.adapter.MyChildListAdapter;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.itemmargin.SpacesItemDecoration;
import com.bwie.myjd.jsonbean.ChildList;
import com.bwie.myjd.presenter.RequestrPresenter;
import com.bwie.myjd.toast.ToastShow;
import com.bwie.myjd.view.OkRequestView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

public class ChildClassifyListActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.ll_zonghe)
    LinearLayout llZonghe;
    @InjectView(R.id.ll_xiaoliang)
    LinearLayout llXiaoliang;
    @InjectView(R.id.ll_price)
    LinearLayout llPrice;
    @InjectView(R.id.rv_child_list)
    RecyclerView rvChildList;
    @InjectView(R.id.iv_style)
    ImageView ivStyle;
    @InjectView(R.id.pb_progressbar)
    ProgressBar pbProgressbar;
    @InjectView(R.id.tv_zonghe)
    TextView tvZonghe;
    @InjectView(R.id.iv_zonghe)
    ImageView ivZonghe;
    @InjectView(R.id.tv_xiaoliang)
    TextView tvXiaoliang;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.iv_price_top)
    ImageView ivPriceTop;
    @InjectView(R.id.iv_price_bottom)
    ImageView ivPriceBottom;
    private int pscid;
    private MyChildListAdapter adapter;
    private Boolean isStyle = true;
    private SharedPreferences styleConfig;
    private List<ChildList.DataBean> list = new ArrayList<>();
    private int sort = 0;
    private int page = 1;
    private int i = 0;
    private String flag;
    private String keywords;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childclassify);
        ButterKnife.inject(this);

        initSp();
        initView();
        getPscid();
        initData();
        initClick();
    }

    private void initClick() {

        rvChildList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //当前RecyclerView显示出来的最后一个的item的position
                int lastPosition = -1;
                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        //通过LayoutManager找到当前显示的最后的item的position
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                        //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);

                    }

                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        ToastShow.showToast(ChildClassifyListActivity.this, "加载中");
                        pbProgressbar.setVisibility(View.VISIBLE);
                        page++;
                        initData();

                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    private void initSp() {
        styleConfig = getSharedPreferences("styleConfig", MODE_PRIVATE);
    }

    /**
     * 初始化
     */
    private void initView() {
        tvZonghe.setTextColor(ContextCompat.getColor(this,R.color.red_text_color));
        ivZonghe.setColorFilter(ContextCompat.getColor(this,R.color.red_text_color));

        ivStyle.setOnClickListener(this);
        llPrice.setOnClickListener(this);
        llXiaoliang.setOnClickListener(this);
        llZonghe.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvChildList.setLayoutManager(layoutManager);
        SpacesItemDecoration decoration = new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.y3), getResources().getDimensionPixelSize(R.dimen.y3), true);
        rvChildList.addItemDecoration(decoration);
    }

    /**
     * 获取商品列表
     */
    private void initData() {
        if(flag.equals("0")){
            //分类
            url = Api.PRODUCT_LIST + "?pscid=" + pscid + "&&" + "sort=" + sort + "&&page=" + page;
        }else if(flag.equals("1")){
            //搜索
            url = Api.SEARCH_API + "?keywords=" + keywords + "&page=" + page + "&sort=" + sort;
        }

        System.out.println("url==" + url);
        RequestrPresenter presenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    System.out.println("string==" + string);
                    Gson gson = new Gson();
                    ChildList childList = gson.fromJson(string, ChildList.class);
                    final List<ChildList.DataBean> data = childList.data;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            pbProgressbar.setVisibility(View.GONE);

                            if (data != null) {
                                list.addAll(data);
                                if (adapter == null) {
                                    System.out.println("重新设置adapter");
                                    rvChildList.setLayoutManager(new LinearLayoutManager(ChildClassifyListActivity.this));
                                    adapter = new MyChildListAdapter(ChildClassifyListActivity.this, list);
                                    rvChildList.setAdapter(adapter);
                                } else {
                                    System.out.println("刷新(追加)");
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            adapter.setOnItemClickListener(new MyChildListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(ChildClassifyListActivity.this, DetailsActivity.class);
                                    intent.putExtra("pid", list.get(position).pid);
                                    System.out.println("pid====" + list.get(position).pid);
                                    startActivity(intent);

                                }
                            });
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

    private void getPscid() {
        flag = getIntent().getStringExtra("flag");
        keywords = getIntent().getStringExtra("keywords");
        pscid = getIntent().getIntExtra("pscid", -1);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_style:
                if (isStyle == true) {
                    ivStyle.setImageResource(R.drawable.style_list);
                    adapter.setType(1);
                    rvChildList.setLayoutManager(new GridLayoutManager(this, 2));
                    rvChildList.setAdapter(adapter);
                    isStyle = false;
                } else {
                    ivStyle.setImageResource(R.drawable.style_grid);
                    adapter.setType(0);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    rvChildList.setLayoutManager(layoutManager);
                    rvChildList.setAdapter(adapter);
                    isStyle = true;
                }
                break;

            case R.id.ll_zonghe:
                list.clear();
                tvXiaoliang.setTextColor(ContextCompat.getColor(this, R.color.black_black_text_color));
                tvZonghe.setTextColor(ContextCompat.getColor(this,R.color.red_text_color));
                ivZonghe.setColorFilter(ContextCompat.getColor(this,R.color.red_text_color));
                tvPrice.setTextColor(ContextCompat.getColor(this,R.color.black_black_text_color));
                ivPriceBottom.setColorFilter(ContextCompat.getColor(this,R.color.black_black_text_color));
                ivPriceTop.setColorFilter(ContextCompat.getColor(this,R.color.black_black_text_color));
                sort = 0;
                initData();
                break;

            case R.id.ll_xiaoliang:
                list.clear();
                tvXiaoliang.setTextColor(ContextCompat.getColor(this,R.color.red_text_color));
                tvZonghe.setTextColor(ContextCompat.getColor(this,R.color.black_black_text_color));
                ivZonghe.setColorFilter(ContextCompat.getColor(this,R.color.black_black_text_color));
                tvPrice.setTextColor(ContextCompat.getColor(this,R.color.black_black_text_color));
                ivPriceBottom.setColorFilter(ContextCompat.getColor(this,R.color.black_black_text_color));
                ivPriceTop.setColorFilter(ContextCompat.getColor(this,R.color.black_black_text_color));

                sort = 1;
                initData();
                break;

            case R.id.ll_price:
                list.clear();
                tvXiaoliang.setTextColor(ContextCompat.getColor(this,R.color.black_black_text_color));
                tvZonghe.setTextColor(ContextCompat.getColor(this,R.color.black_black_text_color));
                ivZonghe.setColorFilter(ContextCompat.getColor(this,R.color.black_black_text_color));
                tvPrice.setTextColor(ContextCompat.getColor(this,R.color.red_text_color));
                ivPriceBottom.setColorFilter(ContextCompat.getColor(this,R.color.red_text_color));
                ivPriceTop.setColorFilter(ContextCompat.getColor(this,R.color.red_text_color));
                sort = 2;
                initData();
                break;
        }
    }


}
