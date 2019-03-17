package com.hziee.jinli.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="club_info")
@Data
public class Club {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer clubId;
    private String clubName;
    private String clubType;
    private String clubRegisterTime;
    private int clubAdminNum;

}
