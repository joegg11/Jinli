package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoleExceptionEnum {

    ROLE_UPDATE_ERROR(304, "角色信息修改失败"),
    ROLE_SELECT_ERROR(400, "角色信息查询失败"),
    ROLE_NOT_FOUND(400, "未查询到角色信息");

    private int code;
    private String msg;
}
