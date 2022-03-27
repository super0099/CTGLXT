package com.yxm.po;

import java.io.Serializable;

public class SysFloor implements Serializable {
    //楼层信息表
    //id,floor_place,floor_grade
    private Integer id;
    private String floorPlace;
    private Integer floorGrade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFloorPlace() {
        return floorPlace;
    }

    public void setFloorPlace(String floorPlace) {
        this.floorPlace = floorPlace;
    }

    public Integer getFloorGrade() {
        return floorGrade;
    }

    public void setFloorGrade(Integer floorGrade) {
        this.floorGrade = floorGrade;
    }

    @Override
    public String toString() {
        return "SysFloor{" +
                "id=" + id +
                ", floorPlace='" + floorPlace + '\'' +
                ", floorGrade=" + floorGrade +
                '}';
    }
}
