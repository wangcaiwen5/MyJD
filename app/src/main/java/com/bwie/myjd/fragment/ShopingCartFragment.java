package com.bwie.myjd.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.myjd.MyOrderActivity;
import com.bwie.myjd.PayActivity;
import com.bwie.myjd.R;
import com.bwie.myjd.adapter.MyShoppCartAdapter;
import com.bwie.myjd.api.Api;
import com.bwie.myjd.bean.CheckBoxBean;
import com.bwie.myjd.jsonbean.ShoppCartBean;
import com.bwie.myjd.presenter.RequestrPresenter;
import com.bwie.myjd.toast.ToastShow;
import com.bwie.myjd.view.OkRequestView;
import com.example.myokhttp.CallbackInterface;
import com.example.myokhttp.Method;
import com.example.myokhttp.OkRequestUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Time:2017/9/29 10:26
 * Author:王才文
 * Description:
 */

public class ShopingCartFragment extends Fragment implements View.OnClickListener {


    @InjectView(R.id.tv_bianji)
    TextView tvBianji;
    @InjectView(R.id.ll_top)
    LinearLayout llTop;
    @InjectView(R.id.rv_cart_list)
    RecyclerView rvCartList;
    @InjectView(R.id.cb_selected)
    CheckBox cbSelected;
    @InjectView(R.id.tv_sum_price)
    TextView tvSumPrice;
    @InjectView(R.id.tv_jiesun_sum_num)
    TextView tvJiesunSumnum;
    @InjectView(R.id.ll_jiesuan)
    LinearLayout llJiesuan;
    @InjectView(R.id.ll)
    LinearLayout ll;
    private View view;
    private SharedPreferences isLogin;
    private int uid;
    private MyShoppCartAdapter cartAdapter;
    private List<ShoppCartBean.DataBean> data = new ArrayList<>();
    private List<CheckBoxBean> listparent = new ArrayList<>();
    private String url;
    private int productNum = 0;
    private double productSumPrice = 0.0;
    private DecimalFormat decimalFormat;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isLogin = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        if (view == null) {

            view = View.inflate(getActivity(), R.layout.shopping_cart_fragment, null);


        }


        ButterKnife.inject(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isLogin.getInt("uid", -1) != -1) {
            getUid();
            initView();
            initData();
        }

    }

    /**
     * fragment隐藏显示时执行
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume");


    }

   /* @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){

            if (isLogin.getInt("uid", -1) != -1) {
                productNum=0;
                productSumPrice=0.0;
              initData();
            }
        }

    }*/

    /**
     * 得到用户id
     */
    private void getUid() {
        SharedPreferences isLogin = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        uid = isLogin.getInt("uid", -1);

    }

    /**
     * 初始化购物车数据
     */
    private void initData() {
        final String url = Api.SELECT_CART_API + "?uid=" + uid;
        RequestrPresenter presenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    final String string = response.body().string();
                    System.out.println("购物车==" + string);


                    if (string.length() > 5) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                ShoppCartBean shoppCartBean = gson.fromJson(string, ShoppCartBean.class);
                                data = shoppCartBean.data;
                                boolean flag = true;
                                for (ShoppCartBean.DataBean dataBean : ShopingCartFragment.this.data) {
                                    for (ShoppCartBean.DataBean.ListBean bean : dataBean.list) {
                                        if (bean.selected == 1) {
                                            productSumPrice = productSumPrice + Double.parseDouble(bean.bargainPrice + "") * bean.num;
                                            productNum = productNum + bean.num;
                                        } else {
                                            flag = false;
                                        }
                                    }
                                }
                                tvSumPrice.setText(decimalFormat.format(productSumPrice));
                                tvJiesunSumnum.setText("(" + productNum + ")");

                                cbSelected.setChecked(flag);

                                if (ShopingCartFragment.this.data != null) {
                                    if (cartAdapter == null) {
                                        cartAdapter = new MyShoppCartAdapter(getActivity(), ShopingCartFragment.this.data, listparent);
                                        rvCartList.setAdapter(cartAdapter);

                                        cartAdapter.setCallBack(new MyShoppCartAdapter.onParentcheck() {

                                            @Override//店铺的点击监听
                                            public void OnCheckParentListener(final int position) {


                                                System.out.println("点击了店铺");
                                                ShoppCartBean.DataBean dataBean1 = ShopingCartFragment.this.data.get(position);
                                                for (ShoppCartBean.DataBean.ListBean bean : dataBean1.list) {
                                                    if (bean.selected == 1) {//商品选中时
                                                        productNum = productNum + bean.num;//统计选中商品数量

                                                        productSumPrice = productSumPrice + Double.parseDouble(bean.bargainPrice + "") * bean.num;
                                                    } else {
                                                        productNum = productNum - bean.num;//商品数量
                                                        productSumPrice = productSumPrice - Double.parseDouble(bean.bargainPrice + "") * bean.num;
                                                    }


                                                    String updateurl = Api.UPDATE_CART_API + "?uid=" + uid + "&sellerid=" + bean.sellerid + "&pid=" + bean.pid + "&selected=" + bean.selected + "&num=" + bean.num;
                                                    System.out.println("**点击店铺更新购物车**" + updateurl);
                                                    updateCart(updateurl);


                                                }

                                                tvSumPrice.setText(decimalFormat.format(productSumPrice));
                                                tvJiesunSumnum.setText("(" + productNum + ")");

                                                cartAdapter.notifyDataSetChanged();

                                                boolean flag = true;
                                                for (ShoppCartBean.DataBean dataBean : ShopingCartFragment.this.data) {
                                                    for (ShoppCartBean.DataBean.ListBean listBean : dataBean.list) {
                                                        if (listBean.selected == 0) {
                                                            flag = false;
                                                        }
                                                    }
                                                }

                                                cbSelected.setChecked(flag);

                                            }

                                            @Override//商品的点击监听
                                            public void OnCheckChildListener(final int num, final int parentposition, final int chaildposition) {
                                                System.out.println("点击了商品");
                                                List<ShoppCartBean.DataBean.ListBean> list = ShopingCartFragment.this.data.get(parentposition).list;
                                                ShoppCartBean.DataBean.ListBean bean = list.get(chaildposition);
                                                if (bean.selected == 1) {//商品选中时
                                                    productNum = productNum + bean.num;//统计选中商品数量

                                                    productSumPrice = productSumPrice + Double.parseDouble(bean.bargainPrice + "") * bean.num;
                                                } else {
                                                    productNum = productNum - bean.num;//商品数量
                                                    productSumPrice = productSumPrice - Double.parseDouble(bean.bargainPrice + "") * bean.num;
                                                }
                                                // TODO: 2017/10/20
                                                tvSumPrice.setText(decimalFormat.format(productSumPrice));
                                                tvJiesunSumnum.setText("(" + productNum + ")");


                                                //此处做价格计算逻辑

                                                String sellerid = ShopingCartFragment.this.data.get(parentposition).sellerid;//商户id
                                                int pid = ShopingCartFragment.this.data.get(parentposition).list.get(chaildposition).pid;//商品pid
                                                int num1 = ShopingCartFragment.this.data.get(parentposition).list.get(chaildposition).num;//商品数量
                                                int selected = ShopingCartFragment.this.data.get(parentposition).list.get(chaildposition).selected;//商品是否选中
                                                String updateurl = Api.UPDATE_CART_API + "?uid=" + uid + "&sellerid=" + sellerid + "&pid=" + pid + "&selected=" + selected + "&num=" + num;
                                                System.out.println("**点击商品更新购物车**" + updateurl);
                                                updateCart(updateurl);
                                                cartAdapter.notifyDataSetChanged();

                                                boolean flag = true;
                                                for (ShoppCartBean.DataBean dataBean : ShopingCartFragment.this.data) {
                                                    for (ShoppCartBean.DataBean.ListBean listBean : dataBean.list) {
                                                        if (listBean.selected == 0) {
                                                            flag = false;
                                                        }
                                                    }
                                                }

                                                cbSelected.setChecked(flag);

                                            }


                                            @Override//加减号的点击
                                            public void onProductChangeListener(int num, int parentposition, int chaildposition) {
                                                ShoppCartBean.DataBean.ListBean bean1 = data.get(parentposition).list.get(chaildposition);
                                                if (bean1.selected == 1){
                                                    productNum = productNum + num;
                                                    productSumPrice = productSumPrice + Double.parseDouble(bean1.bargainPrice+"")*num;
                                                }

                                                tvSumPrice.setText(decimalFormat.format(productSumPrice));
                                                tvJiesunSumnum.setText("(" + productNum + ")");
                                                ShoppCartBean.DataBean.ListBean bean = data.get(parentposition).list.get(chaildposition);

                                                String updateurl = Api.UPDATE_CART_API + "?uid=" + uid + "&sellerid=" + bean.sellerid + "&pid=" + bean.pid + "&selected=" + bean.selected + "&num=" + num;
                                                System.out.println("**点击加减更新购物车**" + updateurl);
                                                updateCart(updateurl);

                                            }
                                        });

                                    }else{
                                        cartAdapter.notifyDataSetChanged();
                                    }
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


        presenter.requestData(url);


    }


    /**
     * 更新购物车
     *
     * @param url
     */
    private void updateCart(String url) {

        Map<String, String> map = new HashMap<>();
        OkRequestUtils.call(url, Method.POST, map);
        OkRequestUtils.getInstance(getContext()).setCallback(new CallbackInterface() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    System.out.println("更新购物车==" + string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /*RequestrPresenter presenter = new RequestrPresenter(new OkRequestView() {
            @Override
            public void requestSuccess(Call call, Response response) {
                try {
                    String string = response.body().string();
                    System.out.println("更新购物车==" + string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFail(Call call, IOException e) {

            }
        });


        presenter.requestData(url);*/
    }


    /**
     * 初始化view
     */
    private void initView() {

        decimalFormat = new DecimalFormat("#0.00");
        cbSelected.setOnClickListener(this);
        llJiesuan.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        rvCartList.setLayoutManager(layoutManager);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_jiesuan:
                //创建订单
                createOrder();

                Intent intent = new Intent(getActivity(), PayActivity.class);
                intent.putExtra("sumPrice", productSumPrice + "");
                intent.putExtra("sumNum", productNum + "");
                startActivity(intent);

                break;

            case R.id.cb_selected:
                //全选
                allCheck();
                // TODO: 2017/10/20
                break;

            case R.id.ll_my_order:
                Intent intent1 = new Intent(getActivity(), MyOrderActivity.class);
                intent1.putExtra("sumPrice", productSumPrice + "");
                intent1.putExtra("sumNum", productNum + "");
                startActivity(intent1);
                break;
        }
    }

    /**
     * 创建订单
     */
    private void createOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        map.put("price", productSumPrice + "");
        OkRequestUtils.call(Api.CREATE_ORDER, Method.POST, map);
        OkRequestUtils.getInstance(getActivity()).setCallback(new CallbackInterface() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");

                        ToastShow.showToast(getActivity(), msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 全选
     */
    private void allCheck() {
        if (cbSelected.isChecked()) {
            for (ShoppCartBean.DataBean dataBean : data) {
                for (ShoppCartBean.DataBean.ListBean bean : dataBean.list) {
                    if (bean.selected == 0) {
                        productNum = productNum + bean.num;
                        productSumPrice = productSumPrice + Double.parseDouble(bean.bargainPrice + "") * bean.num;
                        bean.selected = 1;
                    }
                    String updateurl = Api.UPDATE_CART_API + "?uid=" + uid + "&sellerid=" + bean.sellerid + "&pid=" + bean.pid + "&selected=" + bean.selected + "&num=" + bean.num;
                    System.out.println("**点击全选更新购物车**" + updateurl);
                    updateCart(updateurl);
                }
            }
        } else {
            for (ShoppCartBean.DataBean dataBean : data) {
                for (ShoppCartBean.DataBean.ListBean bean : dataBean.list) {
                    if (bean.selected == 1) {
                        productNum = productNum - bean.num;
                        productSumPrice = productSumPrice - Double.parseDouble(bean.bargainPrice + "") * bean.num;
                        bean.selected = 0;
                        String updateurl = Api.UPDATE_CART_API + "?uid=" + uid + "&sellerid=" + bean.sellerid + "&pid=" + bean.pid + "&selected=" + bean.selected + "&num=" + bean.num;
                        System.out.println("**点击全选更新购物车**" + updateurl);
                        updateCart(updateurl);
                    }

                }
            }
        }

        tvSumPrice.setText(decimalFormat.format(productSumPrice));
        tvJiesunSumnum.setText("(" + productNum + ")");

        cartAdapter.notifyDataSetChanged();

    }
}
