package com.personal_game.datn.Response;

import java.io.Serializable;

public class Message_Suggestion implements Serializable {
    private int status;
    private String notification;
    private Suggestion suggestion;

    public Message_Suggestion(int status, String notification, Suggestion suggestion) {
        this.status = status;
        this.notification = notification;
        this.suggestion = suggestion;
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

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }
}
