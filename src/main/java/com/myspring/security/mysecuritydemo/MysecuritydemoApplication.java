package com.myspring.security.mysecuritydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.myspring.security.mysecuritydemo.mapper")
public class MysecuritydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysecuritydemoApplication.class, args);
	}
}
