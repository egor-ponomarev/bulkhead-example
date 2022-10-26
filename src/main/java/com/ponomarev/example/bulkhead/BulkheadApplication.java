package com.ponomarev.example.bulkhead;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application
 * @author Egor Ponomarev
 */
@SpringBootApplication
public class BulkheadApplication {

	public static void main(String[] args) {
		SpringApplication.run(BulkheadApplication.class, args);
	}

}
