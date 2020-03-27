package com.employees.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class EmployeeApplication {

	@Autowired
	DataSource dataSource;

	public static void main(String[] args) {
		//new File(ImageController.uploadDirectory).mkdir();
		SpringApplication.run(EmployeeApplication.class, args);
	}

}
