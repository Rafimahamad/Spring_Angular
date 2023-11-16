package com.project.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/question")
@CrossOrigin("http://localhost:4200")
public class QuestionController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private EmailService emailService;
	

	SimpleDateFormat dateFor=new SimpleDateFormat("yyyy-mm-dd hh:mm a");
//	Date date=new Date();

	
	
	@PostMapping("/saveQuery")
	public ResponseEntity<Questions> addQuery(@Valid @RequestBody Questions question,Principal p){
		try {
			
			String name = p.getName();
			Employee employee = this.employeeService.getEmployeeByEmail(name);
			
			employee.getQuestions().add(question);
			question.setEmp(employee);
			question.setDate(dateFor.format(new Date()));
			this.employeeService.saveEmp(employee);
			System.out.println("query added"+question);
			
		} catch (Exception e) {
		System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(question,HttpStatus.OK);
	}
	
	
	@GetMapping("/allQueries")
	public List<Questions>getAllQuestions(){
	return this.questionService.getAllQuestions();
	}
	
	
	public ResponseEntity<List<Questions>> viewYourQueries(Principal p){
		String name = p.getName();
		Employee employee = this.employeeService.getEmployeeByEmail(name);
		List<Questions> questions = employee.getQuestions();
		return ResponseEntity.ok(questions);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Questions> showQueryDetailsById(@PathVariable("id") int id) {
		
		
		Questions question = this.questionService.findById(id);
		
		return new ResponseEntity<>(question,HttpStatus.OK) ;
	}
	
	@GetMapping("/qemp/{id}")
	public ResponseEntity<Employee>viewQueryEmp(@PathVariable("id") int id){
		Questions question = this.questionService.findById(id);
		
		Employee emp = question.getEmp();
		return new ResponseEntity<>(emp,HttpStatus.OK) ;
		
	}
	
	
	@PostMapping("saveReply/{qId}")
	public ResponseEntity<Answers> saveReply(@Valid @RequestBody Answers answer,@PathVariable("qId") int id,BindingResult result,Principal p){
		
		try {
			if(result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST)	;
			}
			Employee employee = this.employeeService.getEmployeeByEmail(p.getName());
			Questions question = this.questionService.findById(id);
			
			Employee emp = question.getEmp();
			answer.setDate(dateFor.format(new Date()));
			answerService.saveAnswer(answer);
			question.getAnswers().add(answer);
			answer.setEmp(employee);
			answer.setQuestion(question);
			employeeService.saveEmp(employee);
			
			String subject=" from DSRS";
			String content=""
					+ "<div class='card' style='border:1px solid #e2e2e2; padding:20px'>"
					+ "<p>"
					+ "your quey : "
					+"<div style='color: red'>"
					+question.getQuestion()
					+"</div>"
					+"<br>"
					+" answered by "

					+emp.getEmail()
					+"<br>"
					+answer.getAnswer()

					+ "</p>"
					+ "</div>";
			String to = emp.getEmail();

			emailService.sendEmail(subject,content,to);
			
			
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		 return new ResponseEntity<>(HttpStatus.OK)	;
		
	}
	
	
	@DeleteMapping("/{qId}")
	public ResponseEntity<Questions>deleteuestion(@PathVariable("qId") int qId,Principal p){
		try {
			String name = p.getName();
			Employee employee = employeeService.getEmployeeByEmail(name);
			
			Questions question = questionService.findById(qId);
			Employee qemp = question.getEmp();
			List<Answers> answers = question.getAnswers();
			
			if(answers.isEmpty()) {

				if(employee.getId()!=qemp.getId() && employee.getRole().equals("ROLE_USER")) {
					return new ResponseEntity<Questions>(HttpStatus.NOT_ACCEPTABLE);
				}
				
				else {
					
					this.questionService.deleteQuery(qId);
					
					return new ResponseEntity<Questions>(HttpStatus.OK);	
				}
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Questions>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Questions>(HttpStatus.NOT_ACCEPTABLE);
		
	}
	
	
	@PutMapping("/{qId}")
	public ResponseEntity<Questions>updateQuery( @RequestBody Questions question ,@PathVariable("qId") int qId,Principal p){
		
		try {
			String name = p.getName();
			Employee employee = employeeService.getEmployeeByEmail(name);
			Questions query = questionService.findById(qId);
			Employee qemp = query.getEmp();
			if(query.getAnswers().isEmpty() && qemp.getId()==employee.getId() )
			{
				question.setEmp(employee);
				question.setDate(dateFor.format(new Date()));
				question.setQuestion(question.getQuestion());
				this.employeeService.saveEmp(employee);
				
				return ResponseEntity.ok(this.questionService.updateQuestion(question));
			}
			else
				return new ResponseEntity<Questions>(HttpStatus.BAD_REQUEST);
			
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return new ResponseEntity<Questions>(HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping("/getYourQueries")
	public ResponseEntity<List<Questions>> yourQueries(Principal p) {
		String name = p.getName();
		Employee employee = this.employeeService.getEmployeeByEmail(name);
		 List<Questions> questions = employee.getQuestions();
		 return ResponseEntity.ok(questions);
	}
	
}
