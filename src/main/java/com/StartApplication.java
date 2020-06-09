package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StartApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(StartApplication.class);
	
	public static void main(String[] args) {
		LOGGER.info("My Cart Rest Module Start Initiated");
		
		SpringApplication.run(StartApplication.class, args);
		
		LOGGER.info("My Cart Rest Module Statred Sucessfully");
		
	}

	
}
