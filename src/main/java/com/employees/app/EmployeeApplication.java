package com.employees.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		//new File(ImageController.uploadDirectory).mkdir();
		SpringApplication.run(EmployeeApplication.class, args);
	}

}
