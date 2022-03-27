package com.yxm.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class SysMonthVo implements Serializable {
    //Jan,Feb,Mar,Apr,May,June,July,Aug,Sept,Oct,Nov,Dece
    private BigDecimal Jan = new BigDecimal(0);
    private BigDecimal Feb = new BigDecimal(0);
    private BigDecimal Mar = new BigDecimal(0);
    private BigDecimal Apr = new BigDecimal(0);
    private BigDecimal May = new BigDecimal(0);
    private BigDecimal June = new BigDecimal(0);
    private BigDecimal July = new BigDecimal(0);
    private BigDecimal Aug = new BigDecimal(0);
    private BigDecimal Sept = new BigDecimal(0);
    private BigDecimal Oct = new BigDecimal(0);
    private BigDecimal Nov = new BigDecimal(0);
    private BigDecimal Dece = new BigDecimal(0);

    public BigDecimal getOct() {
        return Oct;
    }

    public void setOct(BigDecimal oct) {
        Oct = oct;
    }

    public BigDecimal getJan() {
        return Jan;
    }

    public void setJan(BigDecimal jan) {
        Jan = jan;
    }

    public BigDecimal getFeb() {
        return Feb;
    }

    public void setFeb(BigDecimal feb) {
        Feb = feb;
    }

    public BigDecimal getMar() {
        return Mar;
    }

    public void setMar(BigDecimal mar) {
        Mar = mar;
    }

    public BigDecimal getApr() {
        return Apr;
    }

    public void setApr(BigDecimal apr) {
        Apr = apr;
    }

    public BigDecimal getMay() {
        return May;
    }

    public void setMay(BigDecimal may) {
        May = may;
    }

    public BigDecimal getJune() {
        return June;
    }

    public void setJune(BigDecimal june) {
        June = june;
    }

    public BigDecimal getJuly() {
        return July;
    }

    public void setJuly(BigDecimal july) {
        July = july;
    }

    public BigDecimal getAug() {
        return Aug;
    }

    public void setAug(BigDecimal aug) {
        Aug = aug;
    }

    public BigDecimal getSept() {
        return Sept;
    }

    public void setSept(BigDecimal sept) {
        Sept = sept;
    }

    public BigDecimal getNov() {
        return Nov;
    }

    public void setNov(BigDecimal nov) {
        Nov = nov;
    }

    public BigDecimal getDece() {
        return Dece;
    }

    public void setDece(BigDecimal dece) {
        Dece = dece;
    }

    @Override
    public String toString() {
        return "SysMonthVo{" +
                "Jan=" + Jan +
                ", Feb=" + Feb +
                ", Mar=" + Mar +
                ", Apr=" + Apr +
                ", May=" + May +
                ", June=" + June +
                ", July=" + July +
                ", Aug=" + Aug +
                ", Sept=" + Sept +
                ", Oct=" + Oct +
                ", Nov=" + Nov +
                ", Dece=" + Dece +
                '}';
    }
}
