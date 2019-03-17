package com.hziee.jinli.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="daily_revenue_log")
@Data
public class DailyRevenueLogs {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String date;
    private Double sum;
    private String goodsString;
    private Integer clubId;

    public DailyRevenueLogs(String date,double sum,String goodsString,int clubId){
        this.date = date;
        this.sum = sum;
        this.goodsString = goodsString;
        this.clubId = clubId;
    }

    public DailyRevenueLogs(){

    }
}
