package com.gpdisingapura.timotius.ask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;

/**
 * Created by Marthen on 6/20/16.
 * Modified by Marthen on 15/08/20. - for MySQL db
 */

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    String category;
    String title;
    String question;
    Date postedAt;
    String postedBy; //By default is anonymous..
    String theme;
    Boolean isAnswered;

    public Question() {
        this.category = "undefined";
        this.postedAt = new Date();
        this.postedBy = "anonymous"; //Submit by anonymous..
        this.isAnswered = false;
    };

    public Question(String category, String question, Date postedAt, String postedBy) {
        this.category = category;
        this.question = question;
        this.postedAt = postedAt;
        this.postedBy = postedBy;
        this.isAnswered = false;
    }

    public Question(String category, String question, String postedBy) {
        this.category = category;
        this.question = question;
        this.postedAt = new Date();
        this.postedBy = postedBy;
        this.isAnswered = false;
    }

    //Default constructor
    public Question(String category, String question) {
        this.category = category;
        this.question = question;
        this.postedAt = new Date();
        this.postedBy = "anonymous"; //Submit by anonymous..
        this.isAnswered = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Boolean getAnswered() {
        return isAnswered;
    }

    public void setAnswered(Boolean answered) {
        isAnswered = answered;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                "title='" + title + '\'' +
                "category='" + category + '\'' +
                "question='" + question + '\'' +
                ", postedAt=" + postedAt +
                ", postedBy='" + postedBy + '\'' +
                ", theme='" + theme + '\'' +
                '}';
    }

}
