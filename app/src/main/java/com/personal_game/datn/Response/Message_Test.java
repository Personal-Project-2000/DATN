package com.personal_game.datn.Response;

import java.io.Serializable;
import java.util.List;

public class Message_Test implements Serializable {
    private int status;
    private String notification;
    private List<TestInfo> tests;

    public Message_Test(int status, String notification, List<TestInfo> tests) {
        this.status = status;
        this.notification = notification;
        this.tests = tests;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public List<TestInfo> getTests() {
        return tests;
    }

    public void setTests(List<TestInfo> tests) {
        this.tests = tests;
    }
}
