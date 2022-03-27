package com.yxm.po;

import java.io.Serializable;

public class SysBerth implements Serializable {
    //床位信息表
    //id,berth_location,berth_number,hierarchy,berth_code
    private Integer id;
    private String berthLocation;
    private String berthNumber;
    private Integer hierarchy;
    private Byte berthCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBerthLocation() {
        return berthLocation;
    }

    public void setBerthLocation(String berthLocation) {
        this.berthLocation = berthLocation;
    }

    public String getBerthNumber() {
        return berthNumber;
    }

    public void setBerthNumber(String berthNumber) {
        this.berthNumber = berthNumber;
    }

    public Integer getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Integer hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Byte getBerthCode() {
        return berthCode;
    }

    public void setBerthCode(Byte berthCode) {
        this.berthCode = berthCode;
    }

    @Override
    public String toString() {
        return "SysBerth{" +
                "id=" + id +
                ", berthLocation='" + berthLocation + '\'' +
                ", berthNumber='" + berthNumber + '\'' +
                ", hierarchy=" + hierarchy +
                ", berthCode=" + berthCode +
                '}';
    }
}
