package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Body;
import com.personal_game.datn.Models.PersonalStyle;
import com.personal_game.datn.Models.Purpose;

import java.io.Serializable;
import java.util.List;

public class Suggestion implements Serializable {
    private List<Body> bodies ;
    private List<PersonalStyle> personalStyles ;
    private List<Purpose> purposes ;

    public Suggestion(List<Body> bodies, List<PersonalStyle> personalStyles, List<Purpose> purposes) {
        this.bodies = bodies;
        this.personalStyles = personalStyles;
        this.purposes = purposes;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
    }

    public List<PersonalStyle> getPersonalStyles() {
        return personalStyles;
    }

    public void setPersonalStyles(List<PersonalStyle> personalStyles) {
        this.personalStyles = personalStyles;
    }

    public List<Purpose> getPurposes() {
        return purposes;
    }

    public void setPurposes(List<Purpose> purposes) {
        this.purposes = purposes;
    }
}
