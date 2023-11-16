package com.project.service;

import java.util.List;

import com.project.entity.Answers;

public interface AnswerService {
	
	Answers getAnswerById(int aId);
	Answers saveAnswer(Answers ans);
	void deleteAnswer(Answers ans);
	List<Answers>getAllAnswersByEmpId(int id);

}
