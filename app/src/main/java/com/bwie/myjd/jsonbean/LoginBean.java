package com.bwie.myjd.jsonbean;

/**
 * Time:2017/10/13 17:55
 * Author:王才文
 * Description:
 */

public class LoginBean {

    /**
     * msg : 登录成功
     * code : 0
     * data : {"age":null,"createtime":"2017-10-11T00:00:00","gender":0,"icon":null,"mobile":"18810646920","money":0,"nickname":null,"password":"111111","uid":166,"username":"18810646920"}
     */

    public String msg;
    public String code;
    public DataBean data;

    public static class DataBean {
        /**
         * age : null
         * createtime : 2017-10-11T00:00:00
         * gender : 0
         * icon : null
         * mobile : 18810646920
         * money : 0
         * nickname : null
         * password : 111111
         * uid : 166
         * username : 18810646920
         */

        public Object age;
        public String createtime;
        public int gender;
        public Object icon;
        public String mobile;
        public int money;
        public Object nickname;
        public String password;
        public int uid;
        public String username;
    }
}
