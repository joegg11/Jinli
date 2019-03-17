package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum DiscountExceptionEnum {

    FOUND_DISCOUNT(200, "查询到折扣信息"),
    DISCOUNT_UPDATE_ERROR(304, "折扣方案修改失败"),
    DISCOUNT_SELECT_ERROR(400, "折扣方案列表查询失败"),
    DISCOUNT_ADD_ERROR(401,"折扣方案添加失败"),
    DISCOUNT_DELETE_ERRROR(401,"折扣方案删除失败"),
    DISCOUNT_NOT_FOUND(400, "未查找到该折扣方案");

    private int code;
    private String msg;
}
