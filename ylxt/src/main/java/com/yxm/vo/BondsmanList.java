package com.yxm.vo;

import java.io.Serializable;

public class BondsmanList implements Serializable {
    private String bondsmanName1;
    private String bondsmanName2;
    private Byte relation1;
    private Byte relation2;
    private String IDNumber1;
    private String IDNumber2;
    private String workUnit1;
    private String workUnit2;
    private String homeAddress1;
    private String homeAddress2;
    private String phone1;
    private String phone2;

    public String getBondsmanName1() {
        return bondsmanName1;
    }

    public void setBondsmanName1(String bondsmanName1) {
        this.bondsmanName1 = bondsmanName1;
    }

    public String getBondsmanName2() {
        return bondsmanName2;
    }

    public void setBondsmanName2(String bondsmanName2) {
        this.bondsmanName2 = bondsmanName2;
    }

    public Byte getRelation1() {
        return relation1;
    }

    public void setRelation1(Byte relation1) {
        this.relation1 = relation1;
    }

    public Byte getRelation2() {
        return relation2;
    }

    public void setRelation2(Byte relation2) {
        this.relation2 = relation2;
    }

    public String getIDNumber1() {
        return IDNumber1;
    }

    public void setIDNumber1(String IDNumber1) {
        this.IDNumber1 = IDNumber1;
    }

    public String getIDNumber2() {
        return IDNumber2;
    }

    public void setIDNumber2(String IDNumber2) {
        this.IDNumber2 = IDNumber2;
    }

    public String getWorkUnit1() {
        return workUnit1;
    }

    public void setWorkUnit1(String workUnit1) {
        this.workUnit1 = workUnit1;
    }

    public String getWorkUnit2() {
        return workUnit2;
    }

    public void setWorkUnit2(String workUnit2) {
        this.workUnit2 = workUnit2;
    }

    public String getHomeAddress1() {
        return homeAddress1;
    }

    public void setHomeAddress1(String homeAddress1) {
        this.homeAddress1 = homeAddress1;
    }

    public String getHomeAddress2() {
        return homeAddress2;
    }

    public void setHomeAddress2(String homeAddress2) {
        this.homeAddress2 = homeAddress2;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    @Override
    public String toString() {
        return "BondsmanList{" +
                "bondsmanName1='" + bondsmanName1 + '\'' +
                ", bondsmanName2='" + bondsmanName2 + '\'' +
                ", relation1=" + relation1 +
                ", relation2=" + relation2 +
                ", IDNumber1='" + IDNumber1 + '\'' +
                ", IDNumber2='" + IDNumber2 + '\'' +
                ", workUnit1='" + workUnit1 + '\'' +
                ", workUnit2='" + workUnit2 + '\'' +
                ", homeAddress1='" + homeAddress1 + '\'' +
                ", homeAddress2='" + homeAddress2 + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                '}';
    }
}
