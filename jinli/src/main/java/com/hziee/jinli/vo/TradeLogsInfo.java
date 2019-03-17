package com.hziee.jinli.vo;

import com.hziee.jinli.pojo.Admin;
import com.hziee.jinli.pojo.Goods;
import com.hziee.jinli.pojo.TradeLogs;
import com.hziee.jinli.pojo.VIP;
import lombok.Data;

@Data
public class TradeLogsInfo {
    private Integer vipId;
    private Integer adminId;
    private String vipName;
    private String adminName;
    private Integer goodsId;
    private String goodsName;
    private Integer goodsNum;
    private Double shouldPay;
    private Double actualPay;
    private String date;
    private Integer points;

    public TradeLogsInfo(Admin admin, VIP vip, Goods goods, TradeLogs tradeLogs){
        this.vipId = vip.getVipId();
        this.adminId = admin.getAdminId();
        this.vipName = vip.getName();
        this.adminName = admin.getName();
        this.goodsNum = tradeLogs.getGoodsNum();
        this.goodsId = goods.getGoodsId();
        this.goodsName = goods.getName();
        this.shouldPay = tradeLogs.getShouldPay();
        this.actualPay = tradeLogs.getActualPay();
        this.date = tradeLogs.getDate();
        this.points = tradeLogs.getPoints();
    }

}