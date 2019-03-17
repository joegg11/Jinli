package com.hziee.jinli.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="goods_info")
@Data
public class Goods {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer goodsId;
    private String name;
    private Double price;
    private String unit;
    private Integer pointsId;
    private Integer clubId;
}
