package com.yxm.vo;

import java.io.Serializable;

public class BerthGrade implements Serializable {
    private Integer id;
    private String site;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "BerthGrade{" +
                "id=" + id +
                ", site='" + site + '\'' +
                '}';
    }
}
