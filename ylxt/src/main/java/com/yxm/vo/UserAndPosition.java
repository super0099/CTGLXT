package com.yxm.vo;

import com.yxm.po.SysUser;

import java.io.Serializable;

public class UserAndPosition extends SysUser implements Serializable {
    private String positionName;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "UserAndPosition{" +
                "positionName='" + positionName + '\'' +
                '}';
    }
}
