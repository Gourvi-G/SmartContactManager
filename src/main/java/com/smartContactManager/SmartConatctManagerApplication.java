package com.smartContactManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.support.MultipartFilter;

@SpringBootApplication
public class SmartConatctManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartConatctManagerApplication.class, args);
	}
}
