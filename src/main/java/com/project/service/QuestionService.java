package com.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.project.entity.Questions;

public interface QuestionService {

 Page<Questions> findPaginated(int pageNo,int items);
 List<Questions> findByQuestionContaining(String text);
 Questions findById(int qId);
 Questions saveQuestion(Questions q);
List<Questions> getAllQuestions();
Questions updateQuestion( Questions query);
  //public Page<Questions> findQuestions();

List<Questions>getQuestionsByEmpId(int id);
void deleteQuery(int qId);


}
