package com.gpdisingapura.timotius.ask.service;

import com.gpdisingapura.timotius.ask.model.Question;
import com.gpdisingapura.timotius.ask.model.QuestionDoesNotExistException;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
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
        List<Question> questions = (List<Question>) askRepository.findAllUsersWithPaginationNative(pageable);
        return questions;
    }

//    public List<Question> showInPagination(Integer pageNo, Integer itemPerPage) throws QuestionDoesNotExistException {
//        Query searchUserQuery = new Query(Criteria. where("isAnswered").is(false))
//                .skip((pageNo) * itemPerPage)
//                .limit(itemPerPage)
//                .with(new Sort(Sort.Direction.DESC, "postedAt"));
//        List<Question> questions = askRepository.find(searchUserQuery, Question.class);
//        return questions;
//    }
//
    public int noOfRecordFoundForIsAnsweredIsFalse() {
        return askRepository.noOfRecordFoundForIsAnsweredIsFalse();

    }

//    public Long noOfRecordFoundForIsAnsweredIsFalse() throws QuestionDoesNotExistException {
//        Query searchUserQueryIsAnswered = new Query(Criteria.where("isAnswered").is(false));
//        return mongoOperation.count(searchUserQueryIsAnswered, Question.class);
//
//    }

    public List<Question> findByPostedBy(String name) {
        return askRepository.findByPostedBy(name);
    }

//    public List<Question> findByPostedBy(String name) throws QuestionDoesNotExistException {
//        List<Question> questions = new ArrayList<Question>();
//        // query to search question by name
//        Query searchUserQuery = new Query(Criteria.where("postedBy").is(name));
//        questions = askRepository.find(searchUserQuery, Question.class);
//        return questions;
//    }
//

    public List<Question> findAll(){
        return askRepository.findAllQuestions();
    }

//    public List<Question> findAll() throws QuestionDoesNotExistException {
////        List<Question> questions = new ArrayList<Question>();
////        questions = mongoOperation.findAll(Question.class);
//        Query searchUserQuery = new Query(Criteria.where("isAnswered").is(false))
//                .with(new Sort(Sort.Direction.DESC, "postedAt"));
//        List<Question> questions = askRepository.find(searchUserQuery, Question.class);
//        return questions;
//    }
//
    public void modifyById(Integer questionId) {
        askRepository.modifyById(questionId);
    }

//    public void modifyById(String questionId)
//            throws QuestionDoesNotExistException {
//        askRepository.updateFirst(
//                new Query(Criteria.where("id").is(questionId)),
//                Update.update("isAnswered", true),
//                Question.class);
//    }
//
    public void deleteQuestion(Integer questionId) {
        askRepository.deleteById(questionId);
    }

//    public void deleteQuestion(String questionId) {
//        Question question = mongoOperation.findById(questionId, Question.class);
//        askRepository.remove(question);
//    }
//
    public void deleteAll() {
        askRepository.deleteAll();
    }

//    public void deleteAll() {
//        askRepository.dropCollection(Question.class);
//    }

}
