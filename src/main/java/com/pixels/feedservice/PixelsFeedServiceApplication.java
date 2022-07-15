package com.pixels.feedservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PixelsFeedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PixelsFeedServiceApplication.class, args);
	}

}
