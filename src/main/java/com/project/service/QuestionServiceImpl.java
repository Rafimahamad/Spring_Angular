package com.project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.entity.Questions;
import com.project.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionRepository questionRepository;
	
	
	
	@Override
	public Page<Questions> findPaginated(int pageNo, int items)
	{
		Pageable pageable=PageRequest.of(pageNo, items);
		
		//return this.questionRepository.findAll(pageable);
		
		return this.questionRepository.findAllQuestionsdata(getAllQuestions(), pageable);
	}

	@Override
	public List<Questions> findByQuestionContaining(String text) {

		return questionRepository.findByQuestionContaining(text);
	}

	
	@Override
	public Questions findById(int qId) {
		
		return this.questionRepository.getById(qId);
	}

	@Override
	public Questions saveQuestion(Questions q) {
		
		return this.questionRepository.save(q);
	}

	@Override
	public List<Questions> getAllQuestions() {
		
		return this.questionRepository.findAll();
	}

	@Override
	public Questions updateQuestion(Questions query) {
		return this.questionRepository.save(query);
		
	}

	@Override
	public List<Questions> getQuestionsByEmpId(int id) {
		
		return this.questionRepository.getAllQuestionsByEmpId(id);
	}

	@Override
	public void deleteQuery(int qId) {
		this.questionRepository.deleteById(qId);
		
	}

	
	

}
