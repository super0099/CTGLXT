package com.yxm.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SysWarn implements Serializable {
    private Integer id;
    private String elderName;
    private String elderIdCode;
    private String warnContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validity;
    private String berthNumber;
    private Byte gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    public String getElderIdCode() {
        return elderIdCode;
    }

    public void setElderIdCode(String elderIdCode) {
        this.elderIdCode = elderIdCode;
    }

    public String getWarnContent() {
        return warnContent;
    }

    public void setWarnContent(String warnContent) {
        this.warnContent = warnContent;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public String getBerthNumber() {
        return berthNumber;
    }

    public void setBerthNumber(String berthNumber) {
        this.berthNumber = berthNumber;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "SysWarn{" +
                "id=" + id +
                ", elderName='" + elderName + '\'' +
                ", elderIdCode='" + elderIdCode + '\'' +
                ", warnContent='" + warnContent + '\'' +
                ", dateCreated=" + dateCreated +
                ", validity=" + validity +
                ", berthNumber='" + berthNumber + '\'' +
                ", gender=" + gender +
                '}';
    }
}
