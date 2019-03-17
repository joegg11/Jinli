package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum DailyRevenueExceptionEnum {

    FOUND_SUM(200, "查询到总收银额"),
    SUM_NOT_FOUND(400, "总收银额查询失败"),
    INSERT_DAILY_INFORMATION_ERROR(401, "总营业额信息添加失败"),
    UPDATE_DAILY_INFORMATION_ERROR(304, "总营业额信息修改失败"),
    SUM_NOT_CREATE(401, "总收银额新增失败");

    private int code;
    private String msg;
}
