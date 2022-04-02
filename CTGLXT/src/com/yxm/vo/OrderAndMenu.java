package com.yxm.vo;

import java.io.Serializable;

public class OrderAndMenu implements Serializable {
    private Object order;
    private Object menu;

    public Object getOrder() {
        return order;
    }

    public void setOrder(Object order) {
        this.order = order;
    }

    public Object getMenu() {
        return menu;
    }

    public void setMenu(Object menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "OrderAndMenu{" +
                "order=" + order +
                ", menu=" + menu +
                '}';
    }
}
