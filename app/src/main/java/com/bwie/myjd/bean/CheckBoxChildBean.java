package com.bwie.myjd.bean;

/**
 * Time:2017/10/19 11:06
 * Author:王才文
 * Description:
 */

public class CheckBoxChildBean {

    private Boolean ischidChecked;

    public CheckBoxChildBean(Boolean ischidChecked) {
        this.ischidChecked = ischidChecked;
    }

    public Boolean getChecked() {
        return ischidChecked;
    }

    public void setChecked(Boolean ischidChecked) {
        ischidChecked = ischidChecked;
    }
}
