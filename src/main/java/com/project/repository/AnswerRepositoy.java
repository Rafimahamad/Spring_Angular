package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entity.Answers;



public interface AnswerRepositoy  extends JpaRepository<Answers, Integer>{

	
	@Query("select a from Answers a where a.emp.id=:id")
	public List<Answers>getAnswersByEmpId(int id);
}
