package com.yxm.vo;

import java.io.Serializable;

public class monthColumnarData implements Serializable {
    private String type = "category";
    private String[] data = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "June", "July","Aug","Sept","OCT","Nov","Dece"};

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
