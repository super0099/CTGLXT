package com.yxm.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SysElder implements Serializable {
    //长者信息表
    //id,elder_name,elder_gender,berth_id,elder_birthday,appointment_Id,residence
    private Integer id;
    private String elderName;
    private Byte elderGender;
    private Integer berthId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String appointmentId;
    private String residence;
    private String portrait;
    private String IDNumber;

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

    public Byte getElderGender() {
        return elderGender;
    }

    public void setElderGender(Byte elderGender) {
        this.elderGender = elderGender;
    }

    public Integer getBerthId() {
        return berthId;
    }

    public void setBerthId(Integer berthId) {
        this.berthId = berthId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    @Override
    public String toString() {
        return "SysElder{" +
                "id=" + id +
                ", elderName='" + elderName + '\'' +
                ", elderGender=" + elderGender +
                ", berthId=" + berthId +
                ", birthday=" + birthday +
                ", appointmentId=" + appointmentId +
                ", residence='" + residence + '\'' +
                ", portrait='" + portrait + '\'' +
                ", IDNumber='" + IDNumber + '\'' +
                '}';
    }
}
