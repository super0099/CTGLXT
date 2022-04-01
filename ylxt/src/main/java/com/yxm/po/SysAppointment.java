package com.yxm.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SysAppointment implements Serializable {
    //id,elder_name,gender,age,native_place,nation,marriage,credentials_type,ID_code,birth,residence,home_phone,health_insuranceId,head_portrait,state,nurseType_id,site,start_date,end_date
    private Integer id;
    private String elderName;
    private Byte gender;
    private Integer age;
    private String nativePlace;
    private String nation;
    private Byte marriage;
    private Byte credentialsType;
    private String IDCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    private String residence;
    private String homePhone;
    private Byte healthInsuranceId;
    private String portrait;
    private Byte state;
    private Byte nurseType;
    private Integer site;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String checkInCode;

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

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Byte getMarriage() {
        return marriage;
    }

    public void setMarriage(Byte marriage) {
        this.marriage = marriage;
    }

    public Byte getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(Byte credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getIDCode() {
        return IDCode;
    }

    public void setIDCode(String IDCode) {
        this.IDCode = IDCode;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public Byte getHealthInsuranceId() {
        return healthInsuranceId;
    }

    public void setHealthInsuranceId(Byte healthInsuranceId) {
        this.healthInsuranceId = healthInsuranceId;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Byte getNurseType() {
        return nurseType;
    }

    public void setNurseType(Byte nurseType) {
        this.nurseType = nurseType;
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCheckInCode() {
        return checkInCode;
    }

    public void setCheckInCode(String checkInCode) {
        this.checkInCode = checkInCode;
    }

    @Override
    public String toString() {
        return "SysAppointment{" +
                "id=" + id +
                ", elderName='" + elderName + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", nativePlace='" + nativePlace + '\'' +
                ", nation='" + nation + '\'' +
                ", marriage=" + marriage +
                ", credentialsType=" + credentialsType +
                ", IDCode='" + IDCode + '\'' +
                ", birth=" + birth +
                ", residence='" + residence + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", healthInsuranceId=" + healthInsuranceId +
                ", portrait='" + portrait + '\'' +
                ", state=" + state +
                ", nurseType=" + nurseType +
                ", site='" + site + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", checkInCode=" + checkInCode +
                '}';
    }
}
