package com.gpdisingapura.timotius.ask.controller;

import com.gpdisingapura.timotius.ask.model.Question;
import com.gpdisingapura.timotius.ask.model.QuestionDoesNotExistException;
import com.gpdisingapura.timotius.ask.service.AskService;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marthen on 6/20/16.
 * Modified by Marthen on 15/08/20. - for MySQL db.
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

    @RequestMapping(method = RequestMethod.GET, value = "/benchmark", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<Question>> benchmark() throws QuestionDoesNotExistException {
        List<Question> questions = askService.findAll();

        //== start of testing ==
        //create load..
        final long startTime = System.currentTimeMillis();
        final int iterations = 1_000_000; //1 million iterations..
        for (int i = 1; i <= iterations; i++) {
            questions.stream()
                    .sorted((a, b) -> -a.getId().compareTo(b.getId()))
                    .filter(q -> q.getCategory().equalsIgnoreCase("Leadership"));
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Total time: " + endTime);
        //== end of testing ==

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
        for (Question question : questions) {
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

    @RequestMapping(method = RequestMethod.PUT, value = "/update")
    ResponseEntity<Void> modifyById(
            @RequestParam("id") Integer questionId)
            throws QuestionDoesNotExistException {
        askService.modifyById(questionId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/polyglotjs1", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String polyglot() {
        Context c = Context.create("js");
        // create a Map and store it as JavaScript object (that is what ProxyObject
        // fromMap is for) in the bindings object
        Map<String, Object> backingMap = new HashMap<>();
        backingMap.put("myKey", "myValue");
        backingMap.put("myQuestion", "2*3");
        c.getBindings("js").putMember("hostObject", ProxyObject.fromMap(backingMap));
        // access the Java Map turned JavaScript object in bindings from JavaScript:
        Integer answer = c.eval("js", "print(`your key = ${hostObject.myKey}`);"
                + "hostObject.yourAnswer = eval(hostObject.myQuestion) ; eval(hostObject.yourAnswer)")
                .asInt();
        // the answer is available from the evaluation of the JS snippet
        System.out.println("The Answer to " + backingMap.get("myQuestion") + " via variable answer = " + answer);
        // and also from the updated hostObject/backingMap in the bindings map
        System.out.println("The Answer to " + backingMap.get("myQuestion") + " via backingMap.get(\"yourAnswer\")= " + backingMap.get("yourAnswer"));

        // creating new objects in JavaScript adds them to the bindings object - and
        // makes them accessible in Java
        c.eval("js", "var PI = 3.141592");
        System.out.println("Current contents of Bindings object: " + c.getBindings("js").getMemberKeys());
        Double pi = c.getBindings("js").getMember("PI").asDouble();
        System.out.println("PI according to JavaScript = " + pi);

        return "check the log..";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/polyglotjs2", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String testJS() {
        Context polyglot = Context.create();
        polyglot.eval("js", "print('Hello JavaScript!')");

        Value helloWorldFunction = polyglot.eval("js", "(function(name) { return `Hello ${name}, welcome to the world of JavaScript` })");
        // Use the function
        String greeting = helloWorldFunction.execute("John Doe").asString();
        System.out.println(greeting);

        // Handle Exception Thrown in JavaScript
        try {
            polyglot.eval("js", "print('Hello JavaScript!'); throw 'I do not feel like executing this';");
        } catch (PolyglotException e) {
            System.out.println("Exception caught from JavaScript Execution. Orginal Exception: " + e.getMessage());
        }

        return greeting;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/polyglotjs3", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String calculatorJS() {
        String resultFibonacci = "";
        String resultSqrt = "";
        Context c = Context.create("js");

        try {
            // [1] Create an environment variable of directory that contains "calculator.js"
            //     Example:
            //        export CALCULATOR_JS_DIR=/Users/mluther/java/demos/opensource-virtual-connect/ask-gpdi-tomotius-sg/polyglot
            // [2] Load the file from System.getenv() ..
            File calculatorJS = new File(System.getenv().get("CALCULATOR_JS_DIR") + "/calculator.js");
            c.eval(Source.newBuilder("js", calculatorJS).build());

            Value fibonacciFunction = c.getBindings("js").getMember("fibonacci");
            Integer fibonacciResult = fibonacciFunction.execute(12).asInt();
            resultFibonacci = "Calculation Result for Fibonacci (12) " + fibonacciResult;
            System.out.println(resultFibonacci);

            Value sqrtFunction = c.getBindings("js").getMember("squareRoot");
            Double sqrtResult = sqrtFunction.execute(42).asDouble();
            resultSqrt = "Calculation Result for Square Root (42) " + sqrtResult;
            System.out.println(resultSqrt);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultFibonacci + "  ;  " + resultSqrt;
    }

//    @RequestMapping(value = "/geturl", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public String getURL(HttpServletRequest request) {
//        System.out.println("request.getLocalName(): " + request.getLocalName());
//        System.out.println("request.getLocalAddr(): " + request.getLocalAddr());
//        System.out.println("request.getLocalPort(): " + request.getLocalPort());
//        System.out.println("request.getRemotePort(): " + request.getRemotePort());
//        System.out.println("request.getServerPort(): " + request.getServerPort());
//        System.out.println("request.getRequestURI(): " + request.getRequestURI());
//        System.out.println("request.getRemoteAddr(): " + request.getRemoteAddr());
//        System.out.println("request.getRemoteHost(): " + request.getRemoteHost());
//        System.out.println("request.getMethod(): " + request.getMethod());
//        System.out.println("request.getContextPath(): " + request.getContextPath());
//        System.out.println("request.getRequestURL(): " + request.getRequestURL());
//        System.out.println("request.getProtocol(): " + request.getProtocol());
//
//        StringBuffer requestURL = request.getRequestURL();
//        int index =requestURL.indexOf("/geturl");
//        System.out.println("clean URL " + requestURL.substring(0, index));
//
//        System.out.println("Environtmen variable " + System.getenv().get("CALCULATOR_JS"));
//
//        /*
//        request.getLocalName(): localhost
//        request.getLocalAddr(): 0:0:0:0:0:0:0:1
//        request.getLocalPort(): 8080
//        request.getRemotePort(): 63690
//        request.getServerPort(): 8080
//        request.getRequestURI(): /ask/geturl
//        request.getRemoteAddr(): 0:0:0:0:0:0:0:1
//        request.getRemoteHost(): 0:0:0:0:0:0:0:1
//        request.getMethod(): GET
//        request.getContextPath():
//        request.getRequestURL(): http://localhost:8080/ask/geturl
//        request.getProtocol(): HTTP/1.1
//        clean URL http://localhost:8080/ask
//        Environtmen variable /Users/mluther/java/demos/opensource-virtual-connect/ask-gpdi-tomotius-sg
//         */
//
//        return request.getLocalName();
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
