package com.gpdisingapura.timotius.ask.service;

import com.gpdisingapura.timotius.ask.model.Question;
import com.gpdisingapura.timotius.ask.model.QuestionDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Marthen on 6/20/16.
 * Modified by Marthen on 15/08/20. - for MySQL db.
 */

@Service
public class AskService {

    @Autowired
    AskRepository askRepository;

    public void postQuestionAnonymously(String category, String q) {
        Question question = new Question(category, q);
        askRepository.save(question);
    }

    public void postQuestion(String postedBy, String category, String q) {
        Question question = new Question(category, q, postedBy);
        askRepository.save(question);
    }

    public Question findById(Integer questionId) throws QuestionDoesNotExistException {
        // query to search question by Id
        Optional<Question> question = Optional.ofNullable(
                askRepository.findById(questionId)
                             .orElseThrow(() -> new QuestionDoesNotExistException(questionId)));
        return question.get();
    }

    public List<Question> showInPagination(Integer pageNo, Integer itemPerPage) {
        Pageable pageable = PageRequest.of(pageNo, itemPerPage);
        List<Question> questions = (List<Question>) askRepository.findAll(pageable);
        return questions;
    }

    public int noOfRecordFoundForIsAnsweredIsFalse() {
        return askRepository.noOfRecordFoundForIsAnsweredIsFalse();

    }

    public List<Question> findByPostedBy(String name) {
        return askRepository.findByPostedBy(name);
    }

    public List<Question> findAll(){
        return askRepository.findAllQuestions();
    }

    public void modifyById(Integer questionId) {
        askRepository.modifyById(questionId);
    }

    public void deleteQuestion(Integer questionId) {
        askRepository.deleteById(questionId);
    }

    public void deleteAll() {
        askRepository.deleteAll();
    }

}
