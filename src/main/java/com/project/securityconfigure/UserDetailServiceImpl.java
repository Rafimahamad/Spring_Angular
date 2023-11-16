package com.project.securityconfigure;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.entity.Employee;
import com.project.service.EmployeeService;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	static Logger log=LoggerFactory.getLogger(UserDetailServiceImpl.class);

	@Autowired
	private EmployeeService employeeService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		//fetch user data from database
		
		
		
		
		Employee employee = employeeService.getEmployeeByEmail(username);
		log.info("fetch employee by email {}",employee);
		if(employee ==null) {
		
			throw new UsernameNotFoundException("could not found user !");
		}
		
		
		return new CustomUserDetails(employee);
	}

}
