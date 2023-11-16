package com.project.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Answers;
import com.project.entity.Employee;
import com.project.entity.Questions;
import com.project.service.AnswerService;
import com.project.service.EmployeeService;
import com.project.service.QuestionService;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping("/saveProfile")
	public Employee editProfile(@Valid @RequestBody Employee emp,BindingResult result) {
		try {
			if(result.hasErrors()) {
				System.out.println(result);
			}
			
			
			List<Questions> questionsOfEmp = this.questionService.getQuestionsByEmpId(emp.getId());
			List<Answers> answersOfEmp = this.answerService.getAllAnswersByEmpId(emp.getId());
			
			emp.setQuestions(questionsOfEmp);
			emp.setAnswers(answersOfEmp);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return this.employeeService.saveEmp(emp);
	}
	
	@GetMapping("/changePassword/{password}")
	public ResponseEntity<String>changePassword(@PathVariable("password") String password,Principal p){
		
		try {
			
			
			Employee employee = this.employeeService.getEmployeeByEmail(p.getName());
			
			
//			if(this.passwordEncoder.encode(employee.getPassword()).equals(password))
			if( this.passwordEncoder.matches(password,employee.getPassword()))
			{
				System.out.println("password not changed ----------------------------------");
			
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			else {
				
				employee.setPassword(this.passwordEncoder.encode(password));
            this.employeeService.saveEmp(employee);
            System.out.println("password changed ----------------------------------");
				return new ResponseEntity<>(HttpStatus.OK);
			}
			
		} 
		catch ( Exception e) {
		System.out.println(e);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	
}
