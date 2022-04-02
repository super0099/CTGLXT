package com.yxm.po;

import java.io.Serializable;

public class dbUser implements Serializable {
    private static final long serialVersionUID = 3790716521201063736L;
    private int Id;
    private String userName;
    private String userPassword;
    private Integer userType;
    private Integer userPrivilege;
    private String userPhone;
    private String userIdnumber;
    private String nickname;
    private String portrait;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserPrivilege() {
        return userPrivilege;
    }

    public void setUserPrivilege(Integer userPrivilege) {
        this.userPrivilege = userPrivilege;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserIdnumber() {
        return userIdnumber;
    }

    public void setUserIdnumber(String userIdnumber) {
        this.userIdnumber = userIdnumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Override
    public String toString() {
        return "dbUser{" +
                "Id=" + Id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userType=" + userType +
                ", userPrivilege=" + userPrivilege +
                ", userPhone='" + userPhone + '\'' +
                ", userIdnumber='" + userIdnumber + '\'' +
                ", nickname='" + nickname + '\'' +
                ", portrait='" + portrait + '\'' +
                '}';
    }
}
