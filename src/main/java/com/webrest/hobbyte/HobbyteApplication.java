package com.webrest.hobbyte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.webrest.hobbyte")
@EnableJpaRepositories
@EntityScan
public class HobbyteApplication {

	public static void main(String[] args) {
		SpringApplication.run(HobbyteApplication.class, args);
	}

}