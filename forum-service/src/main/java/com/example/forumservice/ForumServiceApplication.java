package com.example.forumservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ForumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumServiceApplication.class, args);
	}

}
