package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ClubExceptionEnum {
    CLUB_INFORMATION_CHECK_ERROR(400, "会所信息查询错误"),
    CLUB_INFORMATION_CREATE_ERROR(401, "会所信息登记失败"),
    CLUB_INFORMATION_UPDATE_ERROR(304, "会所信息修改错误");
    private int code;
    private String msg;
}
