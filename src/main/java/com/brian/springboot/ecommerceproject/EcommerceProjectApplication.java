package com.brian.springboot.ecommerceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// 涵蓋 @SpringBootConfiguration, @EnableAutoConfiguration, @ComponentScan
@SpringBootApplication
public class EcommerceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceProjectApplication.class, args);
	}

}
