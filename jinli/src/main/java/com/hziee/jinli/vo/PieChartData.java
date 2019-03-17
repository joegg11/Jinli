package com.hziee.jinli.vo;

import lombok.Data;

@Data
public class PieChartData {
    private String name;
    private Integer data;

    public PieChartData(String name, Integer goodsNum) {
        this.name = name;
        this.data = goodsNum;
    }
}
