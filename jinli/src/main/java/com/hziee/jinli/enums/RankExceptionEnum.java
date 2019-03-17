package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RankExceptionEnum {

    RANK_CREATE_ERROR(401, "会员等级添加失败"),
    RANK_DELETE_ERROR(401, "会员等级注销失败"),
    RANK_UPDATE_ERROR(304, "会员等级信息修改失败"),
    RANK_SELECT_ERROR(400, "会员方案查询失败"),
    RANK_NOT_FOUND(400, "未查询到该级别会员信息");
    private int code;
    private String msg;
}
