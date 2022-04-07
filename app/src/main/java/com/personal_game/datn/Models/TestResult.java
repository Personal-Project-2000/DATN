package com.personal_game.datn.Models;

import java.io.Serializable;

public class TestResult implements Serializable {
    private String answer ;
    private String description ;

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
