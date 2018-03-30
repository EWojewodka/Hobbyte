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
		System.out.println("!@#!#@!@#" + args.length);
		if (args.length > 0) {
			for(String s : args)
				System.out.println("XDXDXDXDXD" + s);
		}
		System.out.println(System.getProperty("spring.profiles.active"));
		SpringApplication.run(HobbyteApplication.class, args);
	}

}