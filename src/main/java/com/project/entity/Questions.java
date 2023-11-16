package com.project.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Question")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Questions {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int qId;

	private String date;
	@NotBlank(message = "* this field is required")
	@Column(unique = true)
	@Size(max = 5000)
	private String question;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<Answers> answers;
@ManyToOne
@JsonIgnore
private Employee emp;



public Questions() {
	
}





public Questions(int qId, String date,String question) {
	super();
	this.qId = qId;
	this.date = date;
	this.question = question;
	
}





public int getqId() {
	return qId;
}

public void setqId(int qId) {
	this.qId = qId;
}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

public String getQuestion() {
	return question;
}

public void setQuestion(String question) {
	this.question = question;
}

public List<Answers> getAnswers() {
	return answers;
}

public void setAnswers(List<Answers> answers) {
	this.answers = answers;
}

public Employee getEmp() {
	return emp;
}

public void setEmp(Employee emp) {
	this.emp = emp;
}

@Override
public String toString() {
	return "Questions [qId=" + qId + ", date=" + date + ", question=" + question + "]";
}



}
