package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Answers;
import com.project.repository.AnswerRepositoy;

@Service
public class AnswerServiceImpl  implements AnswerService{

@Autowired	
private	AnswerRepositoy answerRepositoy;

	
	@Override
	public Answers getAnswerById(int aId) {
		
		return this.answerRepositoy.getById(aId);
	}

	@Override
	public Answers saveAnswer(Answers ans) {
		
		return this.answerRepositoy.save(ans);
	}

	@Override
	public void deleteAnswer(Answers ans) {
		
		this.answerRepositoy.delete(ans);
		
	}

	@Override
	public List<Answers> getAllAnswersByEmpId(int id) {
		
		return this.answerRepositoy.getAnswersByEmpId(id);
	}

}
