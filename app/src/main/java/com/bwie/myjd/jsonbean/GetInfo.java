package com.bwie.myjd.jsonbean;

/**
 * Time:2017/10/19 15:01
 * Author:王才文
 * Description:
 */

public class GetInfo {

    /**
     * msg : 获取用户信息成功
     * code : 0
     * data : {"age":null,"createtime":"2017-10-11T00:00:00","gender":0,"icon":"http://120.27.23.105/images/169.jpg","mobile":"18813146920","money":0,"nickname":"王才文","password":"123456","uid":169,"username":"18813146920"}
     */

    public String msg;
    public String code;
    public DataBean data;

    public static class DataBean {
        /**
         * age : null
         * createtime : 2017-10-11T00:00:00
         * gender : 0
         * icon : http://120.27.23.105/images/169.jpg
         * mobile : 18813146920
         * money : 0
         * nickname : 王才文
         * password : 123456
         * uid : 169
         * username : 18813146920
         */

        public Object age;
        public String createtime;
        public int gender;
        public String icon;
        public String mobile;
        public int money;
        public String nickname;
        public String password;
        public int uid;
        public String username;
    }
}
