package com.yxm.vo;

import java.io.Serializable;
import java.util.List;

public class LayuiTableData<T> implements Serializable {
    private static final long serialVersionUID = -2179577071408418749L;
    private int code;
    private String msg;
    private int count;
    private List<T> data;



    public LayuiTableData(int count, List<T> data) {
        this.count = count;
        this.data = data;
    }

    public LayuiTableData() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
