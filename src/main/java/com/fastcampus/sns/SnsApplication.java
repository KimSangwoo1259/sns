package com.fastcampus.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class) // db기본 설정 안하고 처음에 스프링을 띄우기 위해
public class SnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnsApplication.class, args);
	}

}
