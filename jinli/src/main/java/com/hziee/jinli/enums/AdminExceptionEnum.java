package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AdminExceptionEnum {

    FOUND_ADMIN(200, "查询到管理员"),
    ADMIN_LOGIN_ERROR(404, "管理员登录失败"), //201应该是正确的状态码吧...
    ADMIN_DELETE_ERROR(401, "管理员删除失败"),
    ADMIN_USERNAME_NULL_ERROR(400, "员工账号不能为空"),
    ADMIN_PASSWORD_NULL_ERROR(400, "员工密码不能为空"),
    ADMIN_UPDATE_ERROR(304, "管理员更新失败"),
    ADMIN_CREATE_ERROR(401, "管理员新增失败"),
    ADMIN_CREATE_NAME_REPETITIVE_ERROR(409, "员工添加员工重名"),
    TRADE_LOGS_SELECT_ERROR(403, "交易日志信息查看失败"),
    ADMIN_NOT_FOUND(404, "未查询到管理员");

    private int code;
    private String msg;
}
