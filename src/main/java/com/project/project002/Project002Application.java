package com.project.project002;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@MapperScan("com.project.project002.dao")
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class Project002Application {

	public static void main(String[] args) {
		SpringApplication.run(Project002Application.class, args);
	}

}
