package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>  {

	
	@Query("select e from Employee e where e.email=:email")
	public Employee getEmployeebyemail(@Param("email")  String username);
 
	@Query("select e from Employee e where e.role !='ROLE_ADMIN' ")
   public List<Employee> getEmployesData();
	
}
 