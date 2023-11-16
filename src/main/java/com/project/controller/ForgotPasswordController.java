package com.project.controller;

import java.security.Principal;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Employee;

import com.project.service.EmailService;
import com.project.service.EmployeeService;


@RestController
@RequestMapping("/forgot")
@CrossOrigin("http://localhost:4200")
public class ForgotPasswordController {

	@Autowired
	private EmailService  emailService;
	
	@Autowired
	
	private EmployeeService employeeService;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	static Logger log=LoggerFactory.getLogger(ForgotPasswordController.class);

	
	Random random = new Random(10000);
	
	
	int otp;
	String userEmail;
	

	

//	send OTP......
	@GetMapping("/sendOtp/{email}")
	public ResponseEntity<String> sendOtp(@PathVariable("email") String email) {
		
	log.info("otp Send to this email {}",email);
		
//		generate otp of 4-digit
	
		 otp = random.nextInt(9999);
		 userEmail=email;
		System.out.println(otp+"----------------------------------");
		log.info("generated Otp : {}",otp);
		
		 Employee employee = this.employeeService.getEmployeeByEmail(email);
		
		if(employee==null) {
					
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
		else {
		//write code to send to your email
		
		String subject="OTP from DSRS";
		String message=""
				+ "<div style='border:1px solid #e2e2e2; padding:20px'>"
				+ "<h1>"
				+ "OTP is : "
				+ "<b>"+otp
				+ "</n"
				+ "</h1>"
				+ "</div>";
		String to=email;
				
		boolean sendEmail = this.emailService.sendEmail(subject, message, to);
		
		if(sendEmail) {
		
			return new ResponseEntity<String>(HttpStatus.OK);
			
		}
		else {
		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		}
	}
	
	
//	verify OTP.....
	
	@GetMapping("/validateOtp/{votp}")
	public ResponseEntity<String> verifyotp(@PathVariable("votp") int votp){

		System.out.println(votp+"  ------------------------------------ "+otp);
	if(votp==otp) {
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	else {
		return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	}

	
	

	@GetMapping("/changePassword/{password}")
	public ResponseEntity<String>changePassword(@PathVariable("password") String password){
		
		try {
			
	
				Employee employee = this.employeeService.getEmployeeByEmail(userEmail);
				employee.setPassword(this.passwordEncoder.encode(password));
            this.employeeService.saveEmp(employee);
            System.out.println("password changed ----------------------------------");
				return new ResponseEntity<>(HttpStatus.OK);
		
			
		} 
		catch ( Exception e) {
		System.out.println(e);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/contactAdmin/{email}")
     public ResponseEntity<String> contactAdmin(@PathVariable("email") String email ){
		
		try {
			
			Employee employee = this.employeeService.getEmployeeByEmail(email);
			if(employee!=null) {
				if(employee.getRole().equals("BLOCK")){
					
					String subject="from DSRS";
					String message=""
							+ "<div class='card' style='border:1px solid #e2e2e2; padding:20px'>"
							+"<p> please activate my id </p>"
							+email
							+ "</div>";
					
					String to="mahmadrafi@gmail.com";
					this.emailService.sendEmail(subject, message, to);
					return new ResponseEntity<String>(HttpStatus.OK);
				}
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
}
