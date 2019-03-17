package com.hziee.jinli.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="vip_info")
@Data
public class VIP {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer vipId;
    private String name;
    private String pwd;
    private Integer rankId;
    private Long telephone;
    private Integer daysEffective;
    private Integer points;
    private String flashDay;
    private Integer clubId;

}
