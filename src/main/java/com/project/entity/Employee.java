package com.project.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;




@Entity
@Table(name="Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "* first name is required")
	private String firstName;
	@NotBlank(message = "* last name is required")
	private String lastName;

	private String role;

	@Pattern(regexp = "^[a-z0-9+_.-]+@gmail.com+$",message ="* please provide a valid email address")
	@Column(unique=true)
	private String email;
	@NotBlank(message = "* date of joining is required")
	private String doj;
	@NotBlank(message = "* gender is required")
	private String gender;

	private String profile;

	@NotBlank(message = "* phone number is required")
	@Size(min=10,max=10  ,message="phone number should be 10 digits")
	private String phoneNo;

	@NotBlank(message = "* designation is required")
	private String designation;
	@NotBlank(message = "* password is required")

	@Pattern(regexp ="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}",
	message = "Must contain at least one number and one uppercase and lowercase letter, and at least 6 or more characters")
	private String password;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "emp",orphanRemoval = true ,fetch = FetchType.LAZY)
	private List<Questions> questions=new ArrayList<>(); 

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "emp",orphanRemoval = true ,fetch = FetchType.LAZY)
	private List<Answers> answers;

	
	
	
	public Employee() {
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDoj() {
		return doj;
	}


	public void setDoj(String doj) {
		this.doj = doj;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getProfile() {
		return profile;
	}


	public void setProfile(String profile) {
		this.profile = profile;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<Questions> getQuestions() {
		return questions;
	}


	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}


	public List<Answers> getAnswers() {
		return answers;
	}


	public void setAnswers(List<Answers> answers) {
		this.answers = answers;
	}


	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", role=" + role
				+ ", email=" + email + ", doj=" + doj + ", gender=" + gender + ", profile=" + profile + ", phoneNo="
				+ phoneNo + ", designation=" + designation + ", password=" + password +  "]";
	}
	
	
	
	
	
}
