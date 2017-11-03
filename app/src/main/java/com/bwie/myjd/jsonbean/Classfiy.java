package com.bwie.myjd.jsonbean;

import java.util.List;

/**
 * Time:2017/10/3 21:46
 * Author:王才文
 * Description:
 */

public class Classfiy {
    /**
     * msg :
     * code : 0
     * data : [{"cid":1,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"京东超市"},{"cid":2,"createtime":"2017-10-01T16:18:49","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"全球购"},{"cid":3,"createtime":"2017-10-01T16:00:50","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"手机数码"},{"cid":4,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"全球购"},{"cid":5,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"男装"},{"cid":6,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"女装"},{"cid":7,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"男鞋"},{"cid":8,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"女鞋"},{"cid":9,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"内衣配饰"},{"cid":10,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"箱包手袋"},{"cid":11,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"美妆个护"},{"cid":12,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"钟表珠宝"},{"cid":13,"createtime":"2017-10-01T16:00:50","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"奢侈品"},{"cid":14,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":1,"name":"电脑办公"},{"cid":15,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":0,"name":"家用电器"},{"cid":16,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":0,"name":"食品生鲜"},{"cid":17,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":0,"name":"酒水饮料"},{"cid":18,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":0,"name":"母婴童装"},{"cid":19,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":0,"name":"玩具乐器"},{"cid":20,"createtime":"2017-09-29T10:13:48","icon":"http://120.27.23.105/images/icon.png","ishome":0,"name":"医药保健"}]
     */

    public String msg;
    public String code;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * cid : 1
         * createtime : 2017-09-29T10:13:48
         * icon : http://120.27.23.105/images/icon.png
         * ishome : 1
         * name : 京东超市
         */

        public int cid;
        public String createtime;
        public String icon;
        public int ishome;
        public String name;
    }
}
