package com.zhicaili.shiro.pojo;

import java.io.Serializable;

public class UserRoleVo implements Serializable {
    private Integer userId;
    private String roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
