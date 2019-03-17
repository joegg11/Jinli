package com.hziee.jinli.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="trade_log_info")
@Data
public class TradeLogs {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer tradeId;
    private Integer vipId;
    private Integer adminId;
    private Integer goodsId;
    private Integer goodsNum;
    private Double shouldPay;
    private Double actualPay;
    private String date;
    private Integer clubId;
    private Integer points;
}
