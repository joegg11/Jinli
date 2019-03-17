package com.hziee.jinli.mapper;

import com.hziee.jinli.pojo.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AdminMapper extends Mapper<Admin> {
    //public Admin selectByName(@Param("name") String name);
}
