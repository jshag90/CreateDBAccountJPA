package com.ji.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ji")
public class CreatDBAccountJPA_Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CreatDBAccountJPA_Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
