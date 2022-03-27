package com.yxm.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class IncomeColumnarVo implements Serializable {
    private optionColumnarData optionColumnarData;
    private String stringDate;
    private SysMonthVo sysMonthVo;
    private BigDecimal count;
    private String years;

    public SysMonthVo getSysMonthVo() {
        return sysMonthVo;
    }

    public void setSysMonthVo(SysMonthVo sysMonthVo) {
        this.sysMonthVo = sysMonthVo;
    }

    public com.yxm.vo.optionColumnarData getOptionColumnarData() {
        return optionColumnarData;
    }

    public void setOptionColumnarData(com.yxm.vo.optionColumnarData optionColumnarData) {
        this.optionColumnarData = optionColumnarData;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    @Override
    public String toString() {
        return "IncomeColumnarVo{" +
                "optionColumnarData=" + optionColumnarData +
                ", stringDate='" + stringDate + '\'' +
                ", sysMonthVo=" + sysMonthVo +
                ", count=" + count +
                ", years='" + years + '\'' +
                '}';
    }
}
