package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PointsExceptionEnum {
    FOUND_POINTS(200, "查询到积分信息"),
    POINTS_UPDATE_ERROR(304, "积分方案修改失败"),
    POINTS_SELECT_ERROR(400, "积分列表查询失败"),
    POINTS_ADD_ERROR(401,"积分方案添加失败"),
    POINTS_DELETE_ERROR(401,"积分方案删除失败"),
    POINTS_NOT_FOUND(400, "权限查询失败");

    private int code;
    private String msg;
}
