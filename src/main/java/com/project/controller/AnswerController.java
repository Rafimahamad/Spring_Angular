package com.project.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/answer")
@CrossOrigin("http://localhost:4200")
public class AnswerController {

	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private EmailService emailService;
	
	
	@DeleteMapping("/{aId}/{qId}")
	public ResponseEntity<Answers>deleteAnswer(@PathVariable("aId") int aId,@PathVariable("qId") int qId,Principal p){
		try {
			String name = p.getName();
			Employee employee = employeeService.getEmployeeByEmail(name);
			
			Questions question = questionService.findById(qId);
			
			Answers answer = answerService.getAnswerById(aId);
			Employee aemp = answer.getEmp();
			
			System.out.println("------------------------------------------------------");
			
			if(!aemp.getEmail().equals(employee.getEmail()) && employee.getRole().equals("ROLE_USER")) {
				return new ResponseEntity<Answers>(HttpStatus.NOT_FOUND);
			}
			
			else {
				question.getAnswers().remove(answer);
				questionService.saveQuestion(question);
				answerService.deleteAnswer(answer);
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Answers>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Answers>(HttpStatus.OK);
	}
	
	
	
	@GetMapping("/emp/{aId}")
	public ResponseEntity<Employee> getEmpByaId(@PathVariable("aId") int aId) {
		
		Answers answer = this.answerService.getAnswerById(aId);
		Employee emp = answer.getEmp();
		
		return ResponseEntity.ok(emp);
		
	}
	
	
	@GetMapping("/{aId}")
	public ResponseEntity<Answers> getAnswerById(@PathVariable("aId") int aId) {
		
		Answers answer = this.answerService.getAnswerById(aId);
		
		
		return ResponseEntity.ok(answer);
		
	}
	
}
