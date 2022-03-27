package com.yxm.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SysPosition implements Serializable {
    //职位表字段
    private Integer id;

    private String positionName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "SysPosition{" +
                "id=" + id +
                ", positionName='" + positionName + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
