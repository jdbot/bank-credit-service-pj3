package com.nttdata.bankcreditservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Main Class.
 */
@SpringBootApplication
@EnableEurekaClient
public class BankCreditServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankCreditServiceApplication.class, args);
	}

}
