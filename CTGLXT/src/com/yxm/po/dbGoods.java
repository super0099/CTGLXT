package com.yxm.po;

import java.io.Serializable;

public class dbGoods implements Serializable {
    private static final long serialVersionUID = -7405494406076600118L;
    //Id   carId  menuId  number
    private int Id;
    private Integer carId;
    private Integer menuId;
    private Integer number;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "dbGoods{" +
                "Id=" + Id +
                ", carId=" + carId +
                ", menuId=" + menuId +
                ", number=" + number +
                '}';
    }
}
