package com.yxm.vo;

import java.io.Serializable;

public class H5SelectVo implements Serializable {

    private static final long serialVersionUID = -9181268284271372338L;

    private String value;//select的value
    private String text;// select的文本值

    public H5SelectVo() {
    }

    public H5SelectVo(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "H5SelectVo{" +
                "value='" + value + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
