package com.hziee.jinli.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="points_plan")
@Data
public class Points {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer pointsId;
    private String type;
    private Integer pointValue;
    private Integer clubId;
}
