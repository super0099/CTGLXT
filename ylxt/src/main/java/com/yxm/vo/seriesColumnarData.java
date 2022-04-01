package com.yxm.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class seriesColumnarData implements Serializable {
    private List<BigDecimal> data;
    private String type = "bar";

    public List<BigDecimal> getData() {
        return data;
    }

    public void setData(List<BigDecimal> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
