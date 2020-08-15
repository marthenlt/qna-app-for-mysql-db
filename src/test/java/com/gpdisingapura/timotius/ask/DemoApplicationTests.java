//package com.gpdisingapura.timotius.ask;
//
//import com.gpdisingapura.timotius.ask.model.Question;
//import com.gpdisingapura.timotius.ask.model.QuestionDoesNotExistException;
//import com.gpdisingapura.timotius.ask.service.AskService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.List;
//
///**
// * Created by Marthen on 6/20/16.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//public class DemoApplicationTests {
//
//    @Autowired
//    AskService askService;
//
//    @Test
//	public void contextLoads() {
//        try {
//            int itemPerPage = 5;
//            int pageNo = 1; //start from page no.1
//            //Assuming there are total of 19 records where isAnswered is false..
//            List<Question> page1Questions = askService.showInPagination(pageNo, itemPerPage);
//            Assert.assertEquals(page1Questions.size(), itemPerPage);
//            List<Question> page2Questions = askService.showInPagination(pageNo+1, itemPerPage); // page 2
//            Assert.assertEquals(page2Questions.size(), itemPerPage);
//            List<Question> page3Questions = askService.showInPagination(pageNo+2, itemPerPage); //page 3
//            Assert.assertEquals(page3Questions.size(), itemPerPage);
//            List<Question> page4Questions = askService.showInPagination(pageNo+3, itemPerPage); //page 4
//            Assert.assertEquals(page4Questions.size(), 4);
//        } catch (QuestionDoesNotExistException e) {
//            e.printStackTrace();
//        }
//	}
//
//    @Test
//    public void loadDummyData() throws InterruptedException, QuestionDoesNotExistException {
//        for (int i=0; i<50; i++) {
//            askService.postQuestion("Marthen Luther", "Category " + (i+1), "Question no. " + (i+1));
//            System.out.print("record found after adding: " + askService.noOfRecordFoundForIsAnsweredIsFalse());
//        }
//    }
//
//    @Test
//    public void clearAllDummyData() {
//        askService.deleteAll();;
//    }
//
//    @Test
//    public void descOrderTest() {
//        try {
//            int itemPerPage = 10;
//            int pageNo = 1; //start from page no.1
//            //Assuming there are total of 19 records where isAnswered is false..
//            List<Question> page1Questions = askService.showInPagination(pageNo, itemPerPage);
//            Assert.assertEquals(page1Questions.size(), itemPerPage);
//            List<Question> page2Questions = askService.showInPagination(pageNo+1, itemPerPage); // page 2
//            Assert.assertEquals(page2Questions.size(), itemPerPage);
//            List<Question> page3Questions = askService.showInPagination(pageNo+2, itemPerPage); //page 3
//            Assert.assertEquals(page3Questions.size(), itemPerPage);
//            List<Question> page4Questions = askService.showInPagination(pageNo+3, itemPerPage); //page 4
//            Assert.assertEquals(page4Questions.size(), 10);
//        } catch (QuestionDoesNotExistException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
