package com.zhicaili.shiro.pojo;

import java.io.Serializable;

public class RoleVo extends Role implements Serializable {
    private Integer selected;

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "RoleVo{" +
                "selected=" + selected +
                '}';
    }
}
