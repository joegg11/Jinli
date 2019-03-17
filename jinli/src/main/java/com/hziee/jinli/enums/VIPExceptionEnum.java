package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum VIPExceptionEnum {

    FOUND_VIP(200, "查询到会员"),
    VIP_LOGIN_ERROR(400, "会员登录失败"),
    VIP_UPDATE_ERROR(304, "会员修改失败"),
    VIP_CREATE_ERROR(401, "会员新增失败"),
    VIP_CREATE_NAME_REPETITIVE_ERROR(409, "会员注册重名"),
    VIP_USERNAME_NULL_ERROR(400, "会员账号不能为空"),
    VIP_PASSWORD_NULL_ERROR(400, "会员密码不能为空"),
    VIP_DELETE_ERROR(401, "会员删除失败"),
    VIP_RENEW_ERROR(304, "会员续费失败"),
    VIP_SELECT_ERROR(400, "会员列表查询失败"),
    VIP_NO_SUCH_PERMISSION(403,"会员无此权限"),
    VIP_PASSWORD_WRONG(400,"会员密码错误"),
    VIP_NOT_FOUND(400, "未查找到该会员");

    private int code;
    private String msg;

}