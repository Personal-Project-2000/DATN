package com.personal_game.datn.Models;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private String topic ;
    private List<Answer> answers;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
