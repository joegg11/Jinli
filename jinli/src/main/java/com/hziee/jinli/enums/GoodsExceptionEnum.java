package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum GoodsExceptionEnum {
    FOUND_GOODS(200, "查询到物品"),
    GOODS_UPDATE_ERROR(304, "物品修改失败"),
    GOODS_CREATE_ERROR(401, "物品新增失败"),
    GOODS_DELETE_ERROR(401, "物品删除失败"),
    GOODS_RENEW_ERROR(401, "物品续费失败"),
    GOODS_SELECT_ERROR(400, "物品列表查询失败"),
    GOODS_TRADE_ERROR(304 , "物品交易失败"),
    GOODS_NUM_ZERO_ERROR(400, "商品物品不能为零"),
    ACTUAL_PAY_ZERO_ERROR(400, "实际支付不能为零"),
    GOODS_NOT_FOUND(400, "未查找到该物品");

    private int code;
    private String msg;
}
