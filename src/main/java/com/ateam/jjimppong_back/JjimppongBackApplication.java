package com.ateam.jjimppong_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.ateam.jjimppong_back.common.entity")
@EnableJpaRepositories(basePackages = "com.ateam.jjimppong_back.repository")
public class JjimppongBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(JjimppongBackApplication.class, args);
	}
}
