package com.yxm.vo;

import java.io.Serializable;
import java.util.List;

public class optionColumnarData implements Serializable {
    private monthColumnarData xAxis;
    private yAxisColumnarData yAxis;
    private List<seriesColumnarData> series;

    public monthColumnarData getxAxis() {
        return xAxis;
    }

    public void setxAxis(monthColumnarData xAxis) {
        this.xAxis = xAxis;
    }

    public yAxisColumnarData getyAxis() {
        return yAxis;
    }

    public void setyAxis(yAxisColumnarData yAxis) {
        this.yAxis = yAxis;
    }

    public List<seriesColumnarData> getSeries() {
        return series;
    }

    public void setSeries(List<seriesColumnarData> series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "optionColumnarData{" +
                "xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", series=" + series +
                '}';
    }
}
