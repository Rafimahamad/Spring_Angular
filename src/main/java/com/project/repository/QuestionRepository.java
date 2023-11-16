package com.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entity.Questions;

public interface QuestionRepository extends JpaRepository<Questions, Integer> {

	
	//searching
	
	public List<Questions> findByQuestionContaining(String text);


	
	@Query("select q from Questions q ORDER BY qId DESC")
	public Page<Questions> findAllQuestionsdata(List<Questions> q,Pageable pageable );
	
	
	@Query("select q from Questions q where q.emp.id=:id")
	public List<Questions> getAllQuestionsByEmpId(int id);
}
