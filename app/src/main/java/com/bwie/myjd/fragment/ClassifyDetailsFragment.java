package com.bwie.myjd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myjd.R;
import com.bwie.myjd.adapter.MyClassifyDetailsAdapter;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.jsonbean.ChildClassify;
import com.bwie.myjd.presenter.RequestrPresenter;
import com.bwie.myjd.view.OkRequestView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Time:2017/9/29 18:27
 * Author:王才文
 * Description:
 */

public class ClassifyDetailsFragment extends Fragment {


    @InjectView(R.id.rv_child_classify)
    RecyclerView rvChildClassify;
    private int cid;
    private MyClassifyDetailsAdapter adapter;
    private String str;
    private List<ChildClassify.DataBean> data = new ArrayList<>();
    private View classifydetailsview;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (classifydetailsview == null) {
            classifydetailsview = View.inflate(getActivity(), R.layout.classify_details_fragment, null);

        }

        ViewGroup parent = (ViewGroup) classifydetailsview.getParent();
        if (parent != null) {
            parent.removeView(classifydetailsview);
        }

        //上传到github   电风扇 二次
        System.out.println("oncreatView");


        ButterKnife.inject(this, classifydetailsview);
        return classifydetailsview;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onactivityCreated");
        getCid();

        initView();

        requestData();

    }


    private void initView() {

        //设置瀑布流模式
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvChildClassify.setLayoutManager(layout);
        //设置item之间的间隔
        /*SpacesItemDecoration decoration = new SpacesItemDecoration(0);*/
       /* rvListview.addItemDecoration(decoration);*/
    }

    /**
     * 请求数据
     */
    private void requestData() {
        String url = Api.CHILD_CLASSIFY_API + "?cid=" + cid;

        System.out.println("子分类url" + url);
        RequestrPresenter requestrPresenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    System.out.println("子分类==="+string);
                    if (string.length() > 10) {
                        Gson gson = new Gson();
                        ChildClassify childClassify = gson.fromJson(string, ChildClassify.class);
                        data = childClassify.data;

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //添加头布局
                                showAdapter(data);
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

        requestrPresenter.requestData(url);

    }

    /**
     * 设置adapter
     *
     * @param dataBeen
     */
    private void showAdapter(List<ChildClassify.DataBean> dataBeen) {
        if (dataBeen.size() > 0 && dataBeen != null) {

            if (adapter == null) {
                adapter = new MyClassifyDetailsAdapter(dataBeen, getActivity());
                rvChildClassify.setAdapter(adapter);
            }
        }


    }

    /**
     * 得到子分类的cid
     */
    private void getCid() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            cid = bundle.getInt("cid");

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
