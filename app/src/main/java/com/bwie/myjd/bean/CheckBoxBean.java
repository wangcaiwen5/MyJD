package com.bwie.myjd.bean;

import java.util.List;

/**
 * Time:2017/10/18 22:31
 * Author:王才文
 * Description:
 */

public class CheckBoxBean {
    List<CheckBoxChildBean> list;

    private Boolean isChecked;

    public List<CheckBoxChildBean> getList() {
        return list;
    }

    public void setList(List<CheckBoxChildBean> list) {
        this.list = list;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public CheckBoxBean(List<CheckBoxChildBean> list, Boolean isChecked) {
        this.list = list;
        this.isChecked = isChecked;
    }
}
