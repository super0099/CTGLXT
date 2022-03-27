package com.yxm.vo;

import com.yxm.po.SysAppointment;

public class ElderIndentData extends SysAppointment {
    private String nurseTypeName;
    private String checkSite;
    private Byte collectionState;

    public String getNurseTypeName() {
        return nurseTypeName;
    }

    public void setNurseTypeName(String nurseTypeName) {
        this.nurseTypeName = nurseTypeName;
    }

    public String getCheckSite() {
        return checkSite;
    }

    public void setCheckSite(String checkSite) {
        this.checkSite = checkSite;
    }

    public Byte getCollectionState() {
        return collectionState;
    }

    public void setCollectionState(Byte collectionState) {
        this.collectionState = collectionState;
    }

    @Override
    public String toString() {
        return "ElderIndentData{" +
                "nurseTypeName='" + nurseTypeName + '\'' +
                ", checkSite='" + checkSite + '\'' +
                ", collectionState=" + collectionState +
                '}';
    }
}
