package com.zhicaili.shiro.pojo;

import java.io.Serializable;

public class RoleResourcesVo implements Serializable {
    private Integer roleId;
    private String  resourcesId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId;
    }

    @Override
    public String toString() {
        return "RoleResourcesVo{" +
                "roleId=" + roleId +
                ", resourcesId=" + resourcesId +
                '}';
    }
}
