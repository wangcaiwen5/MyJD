package com.bwie.myjd.api;

/**
 * Time:2017/9/30 10:33
 * Author:王才文
 * Description:
 */

public class Api  {

    public static String ip="https://www.zhaoapi.cn";//ip地址
    public static final String CREATE_ORDER=ip+"/product/createOrder";//创建订单
    public static final String ORDERE_LIST=ip+"/product/getOrders";//order列表
    public static final String UPDATE_ORDER=ip+"/product/updateOrder";//order修改
    public static final String GETAD_API=ip+"/ad/getAd";//首页轮播图
    public static final String CLASSIFY_API=ip+"/product/getCatagory";//分类
    public static final String CHILD_CLASSIFY_API=ip+"/product/getProductCatagory";//子分类
    public static final String LOGIN_API=ip+"/user/login";//登录
    public static final String REGISTER_API=ip+"/user/reg";//注册
    public static final String UPLOAD_API=ip+"/file/upload";//上传
    public static final String PRODUCT_DETAIL = ip+"/product/getProductDetail";//商品详情
    public static final String PRODUCT_LIST=ip+"/product/getProducts";//商品
    public static final String SEARCH_API=ip+"/product/searchProducts";//搜索
    public static final String ADD_SHOPPING_CAR_API=ip+"/product/addCart";//添加购物车
    public static final String SELECT_CART_API=ip+"/product/getCarts";//查询购物车
    public static final String UPDATE_CART_API=ip+"/product/updateCarts";//更新购物车
    public static final String GETINFO=ip+"/user/getUserInfo";//获取用户信息
    public static final String DELETE_PRODUCT=ip+"/product/deleteCart";//
}
