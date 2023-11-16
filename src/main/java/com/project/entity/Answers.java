package com.project.entity;

import javax.persistence.Entity;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="Answer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Answers {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int aId;
	
	private String date;
	
	@NotBlank(message = "* this field is required")
	@Size(max = 5000)
	private String answer;
	
	@ManyToOne
	@JsonIgnore
	private Questions question;
	
	@ManyToOne
	@JsonIgnore
	private Employee emp;

	public Answers() {

	}

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Questions getQuestion() {
		return question;
	}

	public void setQuestion(Questions question) {
		this.question = question;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String string) {
		this.date = string;
	}

	@Override
	public String toString() {
		return "Answer [aId=" + aId + ", date=" + date + ", answer=" + answer + "]";
	}

		
	
	
}
