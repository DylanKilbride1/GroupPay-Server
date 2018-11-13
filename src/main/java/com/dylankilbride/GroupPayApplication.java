package com.dylankilbride;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.dylankilbride.grouppay"})//Starting point for application
@EnableAutoConfiguration
public class GroupPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupPayApplication.class, args);
	}
}