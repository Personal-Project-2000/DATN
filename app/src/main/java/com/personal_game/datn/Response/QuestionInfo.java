package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Answer;
import com.personal_game.datn.Models.Question;

import java.io.Serializable;
import java.util.List;

public class QuestionInfo implements Serializable {
    private Question question ;
    private List<Answer> answers ;

    public QuestionInfo(Question question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
