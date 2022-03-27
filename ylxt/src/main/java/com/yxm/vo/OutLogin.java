package com.yxm.vo;

import java.io.Serializable;

public class OutLogin implements Serializable {
    private Integer stateCode;
    private String msg;

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "OutLogin{" +
                "stateCode=" + stateCode +
                ", msg='" + msg + '\'' +
                '}';
    }
}
