package com.dylankilbride;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.dylankilbride.grouppay"})//Starting point for application
@EnableJpaRepositories(basePackages = "com.dylankilbride.grouppay.Repositories")
@EnableAutoConfiguration
public class GroupPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupPayApplication.class, args);
	}
}