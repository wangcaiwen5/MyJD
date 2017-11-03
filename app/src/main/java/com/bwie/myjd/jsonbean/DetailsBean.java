package com.bwie.myjd.jsonbean;

/**
 * Time:2017/10/16 19:20
 * Author:王才文
 * Description:
 */

public class DetailsBean {

    /**
     * msg :
     * seller : {"description":"我是商家18","icon":"http://120.27.23.105/images/icon.png","name":"商家18","productNums":999,"score":5,"sellerid":18}
     * code : 0
     * data : {"bargainPrice":111.99,"createtime":"2017-10-14T21:39:05","detailUrl":"https://item.m.jd.com/product/4719303.html?utm_source=androidapp&utm_medium=appshare&utm_campaign=t_335139774&utm_term=QQfriends","images":"https://m.360buyimg.com/n0/jfs/t9004/210/1160833155/647627/ad6be059/59b4f4e1N9a2b1532.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t7504/338/63721388/491286/f5957f53/598e95f1N7f2adb87.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t7441/10/64242474/419246/adb30a7d/598e95fbNd989ba0a.jpg!q70.jpg","itemtype":1,"pid":2,"price":299,"pscid":1,"salenum":999,"sellerid":18,"subhead":"每个中秋都不能简单，无论身在何处，你总需要一块饼让生活更圆满，京东月饼让爱更圆满京东自营，闪电配送，更多惊喜，快用手指戳一下","title":"北京稻香村 稻香村中秋节月饼 老北京月饼礼盒655g"}
     */

    public String msg;
    public SellerBean seller;
    public String code;
    public DataBean data;

    public static class SellerBean {
        /**
         * description : 我是商家18
         * icon : http://120.27.23.105/images/icon.png
         * name : 商家18
         * productNums : 999
         * score : 5.0
         * sellerid : 18
         */

        public String description;
        public String icon;
        public String name;
        public int productNums;
        public double score;
        public int sellerid;
    }

    public static class DataBean {
        /**
         * bargainPrice : 111.99
         * createtime : 2017-10-14T21:39:05
         * detailUrl : https://item.m.jd.com/product/4719303.html?utm_source=androidapp&utm_medium=appshare&utm_campaign=t_335139774&utm_term=QQfriends
         * images : https://m.360buyimg.com/n0/jfs/t9004/210/1160833155/647627/ad6be059/59b4f4e1N9a2b1532.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t7504/338/63721388/491286/f5957f53/598e95f1N7f2adb87.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t7441/10/64242474/419246/adb30a7d/598e95fbNd989ba0a.jpg!q70.jpg
         * itemtype : 1
         * pid : 2
         * price : 299.0
         * pscid : 1
         * salenum : 999
         * sellerid : 18
         * subhead : 每个中秋都不能简单，无论身在何处，你总需要一块饼让生活更圆满，京东月饼让爱更圆满京东自营，闪电配送，更多惊喜，快用手指戳一下
         * title : 北京稻香村 稻香村中秋节月饼 老北京月饼礼盒655g
         */

        public double bargainPrice;
        public String createtime;
        public String detailUrl;
        public String images;
        public int itemtype;
        public int pid;
        public double price;
        public int pscid;
        public int salenum;
        public int sellerid;
        public String subhead;
        public String title;
    }
}
