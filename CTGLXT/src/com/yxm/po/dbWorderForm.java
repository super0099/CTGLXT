package com.yxm.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class dbWorderForm implements Serializable {
    private static final long serialVersionUID = 2925550026169745961L;
    //Id  orderNumber  orderDate  remark  price   orderStatus  userId  userPhone  site    tip
    private int Id;
    private String orderNumber;
    private Date orderDate;
    private String remark;
    private BigDecimal price;
    private Integer orderStatus;
    private Integer userId;
    private String userPhone;
    private String site;
    private BigDecimal tip;
    private String linkman;

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public BigDecimal getTip() {
        return tip;
    }

    public void setTip(BigDecimal tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return "dbWorderForm{" +
                "Id=" + Id +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderDate=" + orderDate +
                ", remark='" + remark + '\'' +
                ", price=" + price +
                ", orderStatus=" + orderStatus +
                ", userId=" + userId +
                ", userPhone='" + userPhone + '\'' +
                ", site='" + site + '\'' +
                ", tip=" + tip +
                ", linkman='" + linkman + '\'' +
                '}';
    }
}
