package com.bwie.myjd.jsonbean;

import java.util.List;

/**
 * Time:2017/10/22 14:21
 * Author:王才文
 * Description:
 */

public class MyOrder {

    /**
     * msg : 请求成功
     * code : 0
     * data : [{"createtime":"2017-10-21T16:20:49","orderid":513,"price":0,"status":2,"uid":169},{"createtime":"2017-10-21T16:21:50","orderid":514,"price":11800,"status":0,"uid":169},{"createtime":"2017-10-21T16:26:21","orderid":521,"price":111.99,"status":0,"uid":169},{"createtime":"2017-10-22T12:27:40","orderid":732,"price":111.99,"status":0,"uid":169},{"createtime":"2017-10-22T12:39:07","orderid":751,"price":23711.99,"status":0,"uid":169},{"createtime":"2017-10-22T13:24:16","orderid":796,"price":11800,"status":0,"uid":169},{"createtime":"2017-10-22T14:20:27","orderid":837,"price":6399,"status":0,"uid":169},{"createtime":"2017-10-22T14:20:33","orderid":838,"price":4322342342,"status":0,"uid":169}]
     * page : 1
     */

    public String msg;
    public String code;
    public String page;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * createtime : 2017-10-21T16:20:49
         * orderid : 513
         * price : 0
         * status : 2
         * uid : 169
         */

        public String createtime;
        public int orderid;
        public double price;
        public int status;
        public int uid;
    }
}
