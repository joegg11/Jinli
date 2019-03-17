package com.hziee.jinli.vo;

import lombok.Data;

@Data
public class PermissionInfo {
/*

1.会所信息修改
2.会所信息查询
3.员工信息修改
4.员工信息查询
5.会员信息修改
6.会员信息查询
7.物品信息修改
8.物品信息查询
9.积分数据修改
10.积分数据查询
11.折扣方案查询
12.折扣方案修改
13.none 未定义
14.none 未定义
15.none 未定义
16.none 未定义

*/
    private Boolean ClubInfoChange;
    private Boolean ClubInfoSelect;
    private Boolean AdminInfoChange;
    private Boolean AdminInfoSelect;
    private Boolean VipInfoChange;
    private Boolean VipInfoSelect;
    private Boolean GoodsInfoChange;
    private Boolean GoodsInfoSelect;
    private Boolean PointsInfoChange;
    private Boolean PointsInfoSelect;
    private Boolean DiscountInfoChange;
    private Boolean DiscountInfoSelect;

    public  PermissionInfo(){
        this.ClubInfoChange = false;
        this.ClubInfoSelect = false;
        this.AdminInfoChange = false;
        this.AdminInfoSelect = false;
        this.VipInfoChange = false;
        this.VipInfoSelect = false;
        this.GoodsInfoChange = false;
        this.GoodsInfoSelect = false;
        this.PointsInfoChange = false;
        this.PointsInfoSelect = false;
        this.DiscountInfoChange = false;
        this.DiscountInfoSelect = false;
    }
}
