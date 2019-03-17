package com.hziee.jinli.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="admin_info")
@Data
public class Admin {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer adminId;
    private String pwd;
    private String name;
    private Long telephone;
    private Integer role;
    @KeySql(useGeneratedKeys = true)
    private Integer clubId;

}
