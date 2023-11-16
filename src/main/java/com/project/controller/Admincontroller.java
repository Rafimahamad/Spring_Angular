package com.project.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Answers;
import com.project.entity.Employee;
import com.project.entity.Questions;
import com.project.service.AnswerService;
import com.project.service.EmailService;
import com.project.service.EmployeeService;
import com.project.service.QuestionService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:4200")
public class Admincontroller {


	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/empList")
	public List<Employee> getEmployesList(Principal p) {
		String name = p.getName();
		Employee employee = employeeService.getEmployeeByEmail(name);
		List<Employee> list=null;
		if(employee.getRole().equals("ROLE_ADMIN")) {
			list = this.employeeService.getAllEmployesData();
			System.out.println(list);
		}
		
		return list;
	}
	
	
	
	@GetMapping("/unResolved")
	public List<Questions> unResolvedQueries(){
		
		List<Questions> qlist=new ArrayList<>();
		try {
			
			List<Questions> list = questionService.getAllQuestions();
			
			for(Questions quest:list) {
				List<Answers> answers = quest.getAnswers();
				
				if(answers.isEmpty()) {
					qlist.add(quest);
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return qlist;
	}
	
	
	@GetMapping("/block/{id}")
	public ResponseEntity<String> deactivateEmployee(@PathVariable("id") int id,Principal p ){
		try {
			Employee employee = this.employeeService.getEmployeeByID(id);
			
			employee.setRole("BLOCK");
			this.employeeService.saveEmp(employee);
			
			String subject="from DSRS";
			String message=""
					+ "<div class='card' style='border:1px solid #e2e2e2; padding:20px'>"
					+"<p> your id"
					+employee.getEmail()
					+" was blocked by Admin </p>"
					+ "</div>";
			
			String to=employee.getEmail();
					
		 this.emailService.sendEmail(subject, message, to);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	@GetMapping("/activate/{id}")
	public ResponseEntity<String> activateEmployee(@PathVariable("id") int id,Principal p ){
		try {
			Employee employee = this.employeeService.getEmployeeByID(id);
			
			employee.setRole("ROLE_USER");
			this.employeeService.saveEmp(employee);
			
			String subject="from DSRS";
			String message=""
					+ "<div class='card' style='border:1px solid #e2e2e2; padding:20px'>"
					+"<p> your id"
					+employee.getEmail()
					+" was activated by Admin now you can access the application </p>"
					+ "</div>";
			
			String to=employee.getEmail();
					
		 this.emailService.sendEmail(subject, message, to);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
}
