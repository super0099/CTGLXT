package com.yxm.po;

import java.io.Serializable;

public class SysBondsman implements Serializable {
    //bondsman_name,relation,ID_number,work_unit,home_address,phone,appointment_id
    private Integer id;
    private String bondsmanName;
    private Byte relation;
    private String IDNumber;
    private String workUnit;
    private String homeAddress;
    private String phone;
    private String appointmentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBondsmanName() {
        return bondsmanName;
    }

    public void setBondsmanName(String bondsmanName) {
        this.bondsmanName = bondsmanName;
    }

    public Byte getRelation() {
        return relation;
    }

    public void setRelation(Byte relation) {
        this.relation = relation;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public String toString() {
        return "SysBondsman{" +
                "id=" + id +
                ", bondsmanName='" + bondsmanName + '\'' +
                ", relation=" + relation +
                ", IDNumber='" + IDNumber + '\'' +
                ", workUnit='" + workUnit + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", appointmentId=" + appointmentId +
                '}';
    }
}
