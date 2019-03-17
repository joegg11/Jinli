package com.hziee.jinli.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="role_info")
@Data
public class Role {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer roleId;
    private Integer permissionId;
    private String roleName;
}
