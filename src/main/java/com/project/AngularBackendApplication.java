package com.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.entity.Employee;
import com.project.service.EmployeeService;

@SpringBootApplication
public class AngularBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AngularBackendApplication.class, args);
	}

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	@Override
	public void run(String... args) throws Exception {
	try {
		Employee emp=new Employee();
		emp.setFirstName("Mahammad");
		emp.setLastName("Rafi");
		emp.setEmail("mahmadrafi@gmail.com");
		emp.setDesignation("others");
		emp.setGender("male");
		emp.setPhoneNo("7731772014");
		emp.setDoj("01-02-2022");
		emp.setRole("ROLE_ADMIN");
		emp.setProfile("admin.png");
		emp.setPassword(passwordEncoder.encode("Rafi123"));
	employeeService.saveEmp(emp);

	System.out.println("admin was registered");
	
	
	
	
	
	}
	catch (Exception e) {
		System.out.println("admin already registered");	
	}
	}

}
