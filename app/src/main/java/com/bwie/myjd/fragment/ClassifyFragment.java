package com.bwie.myjd.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bwie.myjd.R;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.jsonbean.Classfiy;
import com.bwie.myjd.presenter.RequestrPresenter;
import com.bwie.myjd.toast.ToastShow;
import com.bwie.myjd.view.OkRequestView;
import com.google.gson.Gson;

import java.io.IOException;
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

public class ClassifyFragment extends Fragment {


    @InjectView(R.id.tools)
    LinearLayout tools;
    @InjectView(R.id.tools_scrlllview)
    ScrollView toolsScrlllview;
    @InjectView(R.id.goods_pager)
    ViewPager goodsPager;
    private TextView toolsTextViews[];
    private View view1;
    private View views[];
    private int currentItem = 0;
    private int scrllViewWidth = 0, scrollViewMiddle = 0;
    private ShopAdapter adapter;
    private SharedPreferences sp;
    private List<Classfiy.DataBean> data;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view1 == null) {
            view1 = View.inflate(getActivity(), R.layout.classify_fragment, null);

        }

        ViewGroup parent = (ViewGroup) view1.getParent();
        if (parent != null) {
            parent.removeView(view1);
        }

        ButterKnife.inject(this, view1);
        return view1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initClassfiy();


    }

    private void initView() {
        goodsPager.setOffscreenPageLimit(0);
        tools.removeAllViews();
    }

    private void initClassfiy() {
        RequestrPresenter requestrPresenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();

                    Gson gson = new Gson();
                    Classfiy classfiy = gson.fromJson(string, Classfiy.class);
                    data = classfiy.data;
                    if (data != null && data.size() > 0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToolsView(data);
                                initPager();
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

        requestrPresenter.requestData(Api.CLASSIFY_API);
    }


    //初始化ViewPage
    private void initPager() {

        adapter = new ShopAdapter(getChildFragmentManager());
        if(goodsPager!=null){
            goodsPager.setAdapter(adapter);
            goodsPager.setOnPageChangeListener(onPageChangeListener);
        }


    }

    /**
     * OnPageChangeListener<br/>
     * 监听ViewPager选项卡变化事的事件
     */

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (goodsPager.getCurrentItem() != position) goodsPager.setCurrentItem(position);
            if (currentItem != position) {
                changeTextColor(position);
                changeTextLocation(position);
            }
            currentItem = position;
        }

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private void showToolsView(List<Classfiy.DataBean> toolsList) {

        toolsTextViews = new TextView[toolsList.size()];
        views = new View[toolsList.size()];

        for (int i = 0; i < toolsList.size(); i++) {


            View  tabItemview = View.inflate(getActivity(), R.layout.item_b_top_nav_layout, null);


            tabItemview.setId(i);
            tabItemview.setOnClickListener(toolsItemListener);
            TextView textView = tabItemview.findViewById(R.id.text);
            textView.setText(toolsList.get(i).name);
            if(tools!=null)
            tools.addView(tabItemview);
            toolsTextViews[i] = textView;
            views[i] = tabItemview;
        }
        changeTextColor(0);

    }

    /**
     * 改变textView的颜色
     *
     * @param id
     */
    private void changeTextColor(int id) {
        for (int i = 0; i < toolsTextViews.length; i++) {
            if (i != id) {
                toolsTextViews[i].setBackgroundResource(android.R.color.transparent);
                toolsTextViews[i].setTextColor(0xff000000);
            }
        }
        toolsTextViews[id].setBackgroundResource(R.color.order_list_item_bg);
        toolsTextViews[id].setTextColor(0xffff5d5e);
    }

    /**
     * 改变栏目位置
     *
     * @param clickPosition
     */
    private void changeTextLocation(int clickPosition) {

        int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(views[clickPosition]) / 2));
        toolsScrlllview.smoothScrollTo(0, x);
    }

    /**
     * 返回scrollview的中间位置
     *
     * @return
     */
    private int getScrollViewMiddle() {
        if (scrollViewMiddle == 0)
            scrollViewMiddle = getScrollViewheight() / 2;
        return scrollViewMiddle;
    }

    /**
     * 返回ScrollView的宽度
     *
     * @return
     */
    private int getScrollViewheight() {
        if (scrllViewWidth == 0)
            scrllViewWidth = toolsScrlllview.getBottom() - toolsScrlllview.getTop();
        return scrllViewWidth;
    }

    /**
     * 返回view的宽度
     *
     * @param view
     * @return
     */
    private int getViewheight(View view) {
        return view.getBottom() - view.getTop();
    }

    /**
     * view的点击监听
     */
    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            goodsPager.setCurrentItem(id);
            ToastShow.showToast(getActivity(), id + "");

        }
    };


    /**
     * ViewPager 加载选项卡
     *
     * @author Administrator
     */
    private class ShopAdapter extends FragmentPagerAdapter {
        private ShopAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            Fragment fragment = new ClassifyDetailsFragment();
            Bundle bundle = new Bundle();
            System.out.println("cid===" + data.get(arg0).cid);
            bundle.putInt("cid", data.get(arg0).cid);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
