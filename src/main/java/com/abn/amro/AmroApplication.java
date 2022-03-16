package com.abn.amro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AmroApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmroApplication.class, args);
		System.out.println("Hello world");
	}

}
