package com.project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Employee;
import com.project.exceptions.UserFoundException;
import com.project.service.EmployeeService;

@RestController

@CrossOrigin("http://localhost:4200")
@RequestMapping("/user")
public class HomeController {

	
	@Autowired
	private EmployeeService employeeService;
    
@Autowired
	private BCryptPasswordEncoder passwordEncoder;



@PostMapping(value = "/")
public ResponseEntity<Employee> registerUser(@Valid @RequestBody Employee emp,BindingResult result) throws UserFoundException
{
	
	
	try {
		
		if(result.hasErrors()) {
		System.out.println(result);
		return new ResponseEntity<>((Employee) result,HttpStatus.NOT_FOUND);
		}
		
		
		if(emp.getGender().equals("Female")) {
			emp.setProfile("female.png");
		}
		else {
			emp.setProfile("male.png");
		}
		
		emp.setPassword(passwordEncoder.encode(emp.getPassword()));
		emp.setRole("ROLE_USER");
		Employee employee = this.employeeService.saveEmp(emp);
		System.out.println("registered ----------"+employee);
		
		return new ResponseEntity<Employee>(employee,HttpStatus.OK);
		
	}catch (Exception e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	
	
}


}
