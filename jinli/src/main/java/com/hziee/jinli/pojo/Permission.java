package com.hziee.jinli.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="permission_plan")
@Data
public class Permission {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer permissionId;
    private String permission;
    private Integer clubId;

}
