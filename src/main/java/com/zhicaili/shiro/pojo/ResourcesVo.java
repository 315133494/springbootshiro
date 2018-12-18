package com.zhicaili.shiro.pojo;

import java.io.Serializable;

public class ResourcesVo extends Resources implements Serializable {
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "ResourcesVo{" +
                "checked=" + checked +
                '}';
    }
}
