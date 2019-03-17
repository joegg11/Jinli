package com.hziee.jinli.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="rank_info")
@Data
public class Rank {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer rankId;
    private String rankName;
    private Integer discountId;
    private Integer permissionId;
    private Integer clubId;
}
