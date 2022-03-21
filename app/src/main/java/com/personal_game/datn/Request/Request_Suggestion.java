package com.personal_game.datn.Request;

public class Request_Suggestion {
    private String bodyId ;
    private String personalStyleId ;
    private String purposeId ;
    private Boolean sex ;

    public Request_Suggestion(String bodyId, String personalStyleId, String purposeId, Boolean sex) {
        this.bodyId = bodyId;
        this.personalStyleId = personalStyleId;
        this.purposeId = purposeId;
        this.sex = sex;
    }

    public String getBodyId() {
        return bodyId;
    }

    public void setBodyId(String bodyId) {
        this.bodyId = bodyId;
    }

    public String getPersonalStyleId() {
        return personalStyleId;
    }

    public void setPersonalStyleId(String personalStyleId) {
        this.personalStyleId = personalStyleId;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
}
