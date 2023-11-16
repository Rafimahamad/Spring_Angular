package com.project.controller;

import java.security.Principal;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Employee;
import com.project.entity.JwtRequest;
import com.project.entity.JwtResponse;
import com.project.jwt.JwtUtil;
import com.project.securityconfigure.UserDetailServiceImpl;
import com.project.service.EmployeeService;



@RestController
@CrossOrigin(origins="http://localhost:4200")
public class AuthenticationController {

	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private JwtUtil jwtUtil;


	//generate token


	@PostMapping(value="/token")

	public ResponseEntity<?> generateToken( @RequestBody JwtRequest jwtRequest) throws Exception
	{

	System.out.println("request"+jwtRequest);

	try {


	authenticate(jwtRequest.getEmail(),jwtRequest.getPassword());


	}
	catch (UsernameNotFoundException e)
	{
	e.printStackTrace();
	throw new Exception("User not Found ");
	}

	//authenticate
	
	UserDetails user = this.userDetailServiceImpl.loadUserByUsername(jwtRequest.getEmail());

	String token = this.jwtUtil.generateToken(user);

	System.out.println("get token : "+token);
	

	return ResponseEntity.ok(new JwtResponse(token));

	}

	
	
	private void authenticate(String email,String password) throws Exception
	{
	try {

	this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));


	} catch (DisabledException e) {

	e.printStackTrace();
	throw new Exception("User diabled "+e.getMessage());
	}
	catch (BadCredentialsException e) {
	throw new Exception("Invalid Credentials "+e.getMessage());

	}
	}

	//for getting current User

//	@GetMapping("/currentUser")
//	public Employee getCurrentUser(Principal principal) {
//
//
//	   UserDetails loadUserByUsername = this.userDetailServiceImpl.loadUserByUsername(principal.getName());
//
//	   return (Employee) loadUserByUsername;
//	}
//	
	
	
	@GetMapping("/currentUser")
	public Employee getCurrentUser(Principal principal) {


		return this.employeeService.getEmployeeByEmail(principal.getName());

	    
	}
}
