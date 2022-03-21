package com.personal_game.datn.Models;

import java.io.Serializable;

public class Question implements Serializable {
    private String id ;
    private String topic ;
    private String testId ;

    public Question(String id, String topic, String testId) {
        this.id = id;
        this.topic = topic;
        this.testId = testId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }
}
