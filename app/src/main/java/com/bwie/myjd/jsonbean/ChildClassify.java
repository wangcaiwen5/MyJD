package com.bwie.myjd.jsonbean;

import java.util.List;

/**
 * Time:2017/10/4 16:03
 * Author:王才文
 * Description:
 */

public class ChildClassify {
    /**
     * msg : 获取子分类成功
     * code : 0
     * data : [{"cid":"3","list":[{"icon":"http://120.27.23.105/images/icon.png","name":"耳机/耳麦","pcid":10,"pscid":61},{"icon":"http://120.27.23.105/images/icon.png","name":"音箱/音响","pcid":10,"pscid":62},{"icon":"http://120.27.23.105/images/icon.png","name":"MP3/MP4","pcid":10,"pscid":63},{"icon":"http://120.27.23.105/images/icon.png","name":"便携/无线音箱","pcid":10,"pscid":64},{"icon":"http://120.27.23.105/images/icon.png","name":"麦克风","pcid":10,"pscid":65}],"name":"影音娱乐","pcid":"10"},{"cid":"3","list":[{"icon":"http://120.27.23.105/images/icon.png","name":"游戏手机","pcid":11,"pscid":66},{"icon":"http://120.27.23.105/images/icon.png","name":"拍照手机","pcid":11,"pscid":67},{"icon":"http://120.27.23.105/images/icon.png","name":"大屏手机","pcid":11,"pscid":68},{"icon":"http://120.27.23.105/images/icon.png","name":"老人机","pcid":11,"pscid":69},{"icon":"http://120.27.23.105/images/icon.png","name":"对讲机","pcid":11,"pscid":70}],"name":"手机通讯","pcid":"11"},{"cid":"3","list":[{"icon":"http://120.27.23.105/images/icon.png","name":"蓝牙耳机","pcid":12,"pscid":71},{"icon":"http://120.27.23.105/images/icon.png","name":"移动电源","pcid":12,"pscid":72},{"icon":"http://120.27.23.105/images/icon.png","name":"苹果周边","pcid":12,"pscid":73},{"icon":"http://120.27.23.105/images/icon.png","name":"手机耳机","pcid":12,"pscid":74},{"icon":"http://120.27.23.105/images/icon.png","name":"充电器","pcid":12,"pscid":75},{"icon":"http://120.27.23.105/images/icon.png","name":"数据线","pcid":12,"pscid":76},{"icon":"http://120.27.23.105/images/icon.png","name":"手机电池","pcid":12,"pscid":77},{"icon":"http://120.27.23.105/images/icon.png","name":"保护套","pcid":12,"pscid":78}],"name":"手机配件","pcid":"12"},{"cid":"3","list":[{"icon":"http://120.27.23.105/images/icon.png","name":"单反相机","pcid":13,"pscid":79},{"icon":"http://120.27.23.105/images/icon.png","name":"单电/微单相机","pcid":13,"pscid":80},{"icon":"http://120.27.23.105/images/icon.png","name":"拍立得","pcid":13,"pscid":81},{"icon":"http://120.27.23.105/images/icon.png","name":"数码相机","pcid":13,"pscid":82},{"icon":"http://120.27.23.105/images/icon.png","name":"摄像机","pcid":13,"pscid":83},{"icon":"http://120.27.23.105/images/icon.png","name":"镜头","pcid":13,"pscid":84}],"name":"摄影摄像","pcid":"13"}]
     */

    public String msg;
    public String code;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * cid : 3
         * list : [{"icon":"http://120.27.23.105/images/icon.png","name":"耳机/耳麦","pcid":10,"pscid":61},{"icon":"http://120.27.23.105/images/icon.png","name":"音箱/音响","pcid":10,"pscid":62},{"icon":"http://120.27.23.105/images/icon.png","name":"MP3/MP4","pcid":10,"pscid":63},{"icon":"http://120.27.23.105/images/icon.png","name":"便携/无线音箱","pcid":10,"pscid":64},{"icon":"http://120.27.23.105/images/icon.png","name":"麦克风","pcid":10,"pscid":65}]
         * name : 影音娱乐
         * pcid : 10
         */

        public String cid;
        public String name;
        public String pcid;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * icon : http://120.27.23.105/images/icon.png
             * name : 耳机/耳麦
             * pcid : 10
             * pscid : 61
             */

            public String icon;
            public String name;
            public int pcid;
            public int pscid;
        }
    }
}
