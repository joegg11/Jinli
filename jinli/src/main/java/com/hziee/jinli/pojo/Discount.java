package com.hziee.jinli.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="discount_plan")
@Data
public class Discount {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer discountId;
    private Double discount;
    private Integer clubId;
}
