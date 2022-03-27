package com.yxm.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SysOrder implements Serializable {
    private Integer id;
    private Integer elderId;
    private String elderName;
    private String elderSite;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private Integer breakfast;
    private Integer lunch;
    private Integer dinner;
    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getElderId() {
        return elderId;
    }

    public void setElderId(Integer elderId) {
        this.elderId = elderId;
    }

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    public String getElderSite() {
        return elderSite;
    }

    public void setElderSite(String elderSite) {
        this.elderSite = elderSite;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Integer breakfast) {
        this.breakfast = breakfast;
    }

    public Integer getLunch() {
        return lunch;
    }

    public void setLunch(Integer lunch) {
        this.lunch = lunch;
    }

    public Integer getDinner() {
        return dinner;
    }

    public void setDinner(Integer dinner) {
        this.dinner = dinner;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SysOrder{" +
                "id=" + id +
                ", elderId=" + elderId +
                ", elderName='" + elderName + '\'' +
                ", elderSite='" + elderSite + '\'' +
                ", orderDate=" + orderDate +
                ", breakfast=" + breakfast +
                ", lunch=" + lunch +
                ", dinner=" + dinner +
                ", state=" + state +
                '}';
    }
}
