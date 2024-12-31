package com.ecommers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ECommersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommersApplication.class, args);
	}

}
