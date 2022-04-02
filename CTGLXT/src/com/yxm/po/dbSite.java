package com.yxm.po;

import java.io.Serializable;

public class dbSite implements Serializable {
    private static final long serialVersionUID = 9173150822029393461L;
    private int Id;
    private String site;
    private String linkman;
    private String phone;
    private Integer userId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "dbSite{" +
                "Id=" + Id +
                ", site='" + site + '\'' +
                ", linkman='" + linkman + '\'' +
                ", phone='" + phone + '\'' +
                ", userId=" + userId +
                '}';
    }
}
