package com.yxm.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class dbMenu implements Serializable {
    private static final long serialVersionUID = 7177654254191558439L;
    private int Id;
    private String menuName;
    private BigDecimal price;
    private String introduce;
    private Integer market;
    private Integer collectS;
    private Date putawayDate;
    private String number;
    private Integer chefId;
    private Integer menuTypeId;
    private String picture;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getCollectS() {
        return collectS;
    }

    public void setCollectS(Integer collectS) {
        this.collectS = collectS;
    }

    public Date getPutawayDate() {
        return putawayDate;
    }

    public void setPutawayDate(Date putawayDate) {
        this.putawayDate = putawayDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getChefId() {
        return chefId;
    }

    public void setChefId(Integer chefId) {
        this.chefId = chefId;
    }

    public Integer getMenuTypeId() {
        return menuTypeId;
    }

    public void setMenuTypeId(Integer menuTypeId) {
        this.menuTypeId = menuTypeId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "dbMenu{" +
                "Id=" + Id +
                ", menuName='" + menuName + '\'' +
                ", price=" + price +
                ", introduce='" + introduce + '\'' +
                ", market=" + market +
                ", collectS=" + collectS +
                ", putawayDate=" + putawayDate +
                ", number='" + number + '\'' +
                ", chefId=" + chefId +
                ", menuTypeId=" + menuTypeId +
                ", picture='" + picture + '\'' +
                '}';
    }
}
