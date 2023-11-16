package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.exceptions.UserFoundException;
import com.project.entity.Employee;

@Service
public interface EmployeeService {

public Employee saveEmp(Employee emp) ;
	
public Employee getEmployeeByEmail(String email);

public List<Employee> getAllEmployesData();

public Employee getEmployeeByID(int id);

Employee registerEmp(Employee emp) throws UserFoundException;


}
