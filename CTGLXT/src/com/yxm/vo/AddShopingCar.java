package com.yxm.vo;

import com.yxm.po.dbMenu;

public class AddShopingCar extends dbMenu {
    private Integer carId;
    private Integer amount;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AddShopingCar{" +
                "carId=" + carId +
                ", amount=" + amount +
                '}';
    }
}
