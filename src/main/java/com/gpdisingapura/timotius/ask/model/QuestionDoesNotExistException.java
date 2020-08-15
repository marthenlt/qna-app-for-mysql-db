package com.gpdisingapura.timotius.ask.model;

public final class QuestionDoesNotExistException extends Exception {

    private static final long serialVersionUID = 7996516744853733268L;

    private static final String MESSAGE_FORMAT = "Question '%d' does not exist";

    public QuestionDoesNotExistException(Integer questionId) {
        super(String.format(MESSAGE_FORMAT, questionId));
    }
}