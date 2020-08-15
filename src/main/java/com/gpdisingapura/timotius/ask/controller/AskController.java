package com.gpdisingapura.timotius.ask.controller;

import com.gpdisingapura.timotius.ask.model.Question;
import com.gpdisingapura.timotius.ask.model.QuestionDoesNotExistException;
import com.gpdisingapura.timotius.ask.service.AskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by Marthen on 6/20/16.
 */

@RestController
@RequestMapping("/ask")
public class AskController {

    @Autowired
    AskService askService;

    //default question post..
    @RequestMapping(method = RequestMethod.POST, value = "/post")
    ResponseEntity<Void> postQuestionAnonymously(
            @RequestParam("category") String category,
            @RequestParam("question") String q) {
        askService.postQuestionAnonymously(category, q);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/postBy")
    ResponseEntity<Void> postQuestion(
            @RequestParam("postedBy") String postedBy,
            @RequestParam("category") String category,
            @RequestParam("question") String q) {
        askService.postQuestion(postedBy, category, q);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/show/{questionId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Question> findById(@PathVariable Integer questionId) throws QuestionDoesNotExistException {
        Question question = askService.findById(questionId);
        return new ResponseEntity<Question>(question, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pagination/{pageNo}/{itemPerPage}", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<Question>> showInPagination(
                @PathVariable Integer pageNo,
                @PathVariable Integer itemPerPage
                ) throws QuestionDoesNotExistException {
        List<Question> questions = askService.showInPagination(pageNo, itemPerPage);
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/numberOfUnansweredRecords", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Long> noOfRecordFoundForIsAnsweredIsFalse() throws QuestionDoesNotExistException {
        Long noOfRecs = new Long(askService.noOfRecordFoundForIsAnsweredIsFalse());
        return new ResponseEntity<Long>(noOfRecs, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/show/postedBy/{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<Question>> findByPostedBy(@PathVariable String name) throws QuestionDoesNotExistException {
        List<Question> questions = askService.findByPostedBy(name);
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/showall", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<Question>> findAll() throws QuestionDoesNotExistException {
        List<Question> questions = askService.findAll();
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/downloadQs", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    ResponseEntity<InputStreamResource> downloadQs() throws QuestionDoesNotExistException {
        List<Question> questions = askService.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        StringBuilder sb = new StringBuilder();
        for (Question question: questions) {
            sb.append(question.getId())
                    .append("\t")
                    .append(question.getCategory())
                    .append("\t")
                    .append("\"" + question.getQuestion() + "\"")
                    .append("\t")
                    .append(question.getPostedAt())
                    .append("\t")
                    .append(question.getAnswered())
                    .append("\r\n");
        }
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(sb.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new ByteArrayInputStream(sb.toString().getBytes())));
    }


//    @RequestMapping(method = RequestMethod.PUT, value = "/update")
//    ResponseEntity<Void> modifyById(
//            @RequestParam("id") String questionId)
//            throws QuestionDoesNotExistException {
//        askService.modifyById(questionId);
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }

//    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{questionId}")
//    ResponseEntity<Void> deleteQuestion(@PathVariable String questionId) throws QuestionDoesNotExistException {
//        askService.deleteQuestion(questionId);
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }

//    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteAll")
//    ResponseEntity<Void> deleteAll() throws QuestionDoesNotExistException {
//        askService.deleteAll();
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }


}
