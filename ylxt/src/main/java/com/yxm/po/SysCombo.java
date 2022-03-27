package com.yxm.po;

import java.io.Serializable;

public class SysCombo implements Serializable {
    private Integer id;
    private Integer comboGrade;
    private String comboName;
    private Integer comboType;
    private String stapleFood;
    private String vegetable;
    private String meat;
    private String soup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComboGrade() {
        return comboGrade;
    }

    public void setComboGrade(Integer comboGrade) {
        this.comboGrade = comboGrade;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public Integer getComboType() {
        return comboType;
    }

    public void setComboType(Integer comboType) {
        this.comboType = comboType;
    }

    public String getStapleFood() {
        return stapleFood;
    }

    public void setStapleFood(String stapleFood) {
        this.stapleFood = stapleFood;
    }

    public String getVegetable() {
        return vegetable;
    }

    public void setVegetable(String vegetable) {
        this.vegetable = vegetable;
    }

    public String getMeat() {
        return meat;
    }

    public void setMeat(String meat) {
        this.meat = meat;
    }

    public String getSoup() {
        return soup;
    }

    public void setSoup(String soup) {
        this.soup = soup;
    }

    @Override
    public String toString() {
        return "SysCombo{" +
                "id=" + id +
                ", comboGrade=" + comboGrade +
                ", comboName='" + comboName + '\'' +
                ", comboType=" + comboType +
                ", stapleFood='" + stapleFood + '\'' +
                ", vegetable='" + vegetable + '\'' +
                ", meat='" + meat + '\'' +
                ", soup='" + soup + '\'' +
                '}';
    }
}
