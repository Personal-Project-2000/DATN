package com.personal_game.datn.Models;

import java.io.Serializable;

public class Answer implements Serializable {
    private String id ;
    private String name ;
    private String content ;
    private String questionId ;
    private String img ;

    public Answer(String id, String name, String content, String questionId, String img) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.questionId = questionId;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
