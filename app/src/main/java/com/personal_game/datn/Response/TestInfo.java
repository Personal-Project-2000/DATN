package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Test;
import com.personal_game.datn.Models.TestResult;

import java.io.Serializable;
import java.util.List;

public class TestInfo implements Serializable {
    private Test test ;
    private List<QuestionInfo> questions ;
    private List<TestResult> results ;

    public TestInfo() {
    }

    public TestInfo(Test test, List<QuestionInfo> questions, List<TestResult> results) {
        this.test = test;
        this.questions = questions;
        this.results = results;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<QuestionInfo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionInfo> questions) {
        this.questions = questions;
    }

    public List<TestResult> getResults() {
        return results;
    }

    public void setResults(List<TestResult> results) {
        this.results = results;
    }
}
