package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum StringExceptionEnum {
    PERMISSION_STRING_ERROR(304, "权限数据处理错误");
    private int code;
    private String msg;
}
