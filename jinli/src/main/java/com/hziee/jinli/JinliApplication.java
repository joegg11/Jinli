package com.hziee.jinli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.hziee.jinli.mapper")
public class JinliApplication {

	public static void main(String[] args) {
		SpringApplication.run(JinliApplication.class, args);
	}

}

