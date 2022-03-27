package com.yxm.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SysOut implements Serializable {
    private Integer id;
    private String elderName;
    private String elderIdCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date comeBack;
    private String reason;
    private Byte isOK;

    public Byte getIsOK() {
        return isOK;
    }

    public void setIsOK(Byte isOK) {
        this.isOK = isOK;
    }

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

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Date getComeBack() {
        return comeBack;
    }

    public void setComeBack(Date comeBack) {
        this.comeBack = comeBack;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "SysOut{" +
                "id=" + id +
                ", elderName='" + elderName + '\'' +
                ", elderIdCode='" + elderIdCode + '\'' +
                ", outTime=" + outTime +
                ", comeBack=" + comeBack +
                ", reason='" + reason + '\'' +
                '}';
    }
}
