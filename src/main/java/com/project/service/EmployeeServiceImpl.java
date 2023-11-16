package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.project.exceptions.UserFoundException;
import com.project.entity.Employee;
import com.project.repository.EmployeeRepository;


@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired 
private EmployeeRepository employeeRepository;

	
	
	
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}


	@Override
	public Employee registerEmp(Employee emp) throws UserFoundException {
		
		  Employee employee = this.employeeRepository.getEmployeebyemail(emp.getEmail());
		
		
            if(employee!=null) {
			
			throw new UserFoundException("user already exist");
		}
		
		return this.employeeRepository.save(emp);
	}

	
	@Override
	public Employee saveEmp(Employee emp) {
		
		return this.employeeRepository.save(emp);
	}

	
	@Override
	public Employee getEmployeeByEmail(String email) {
		
		return this.employeeRepository.getEmployeebyemail(email);
	}

	@Override
	public List<Employee> getAllEmployesData() {
		return employeeRepository.getEmployesData();
	}


	@SuppressWarnings("deprecation")
	@Override
	public Employee getEmployeeByID(int id) {
	
		return this.employeeRepository.getById(id);
	}
}
