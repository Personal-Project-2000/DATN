package com.personal_game.datn.Models;

import java.io.Serializable;

public class TestResult implements Serializable {
    private String id ;
    private String testId ;
    private String answer ;
    private String description ;

    public TestResult(String id, String testId, String answer, String description) {
        this.id = id;
        this.testId = testId;
        this.answer = answer;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
