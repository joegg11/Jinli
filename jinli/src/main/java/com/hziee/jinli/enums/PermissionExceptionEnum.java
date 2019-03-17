package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PermissionExceptionEnum {
    FOUND_PERMISSION(200, "查询到权限"),
    PERMISSION_UPDATE_ERROR(304, "权限修改失败"),
    PERMISSION_SELECT_ERROR(400, "权限列表查询失败"),
    ADMIN_NO_SUCH_PERMISSION(403,"管理员无该权限"),
    PERMISSION_NOT_FOUND(400, "权限查询失败");

    private int code;
    private String msg;
}
