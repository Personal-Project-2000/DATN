package com.personal_game.datn.Models;

import java.io.Serializable;
import java.util.List;

public class Coordinate implements Serializable {
    private String id;
    private String user;
    private List<CostumeCoordinate> costumes;

    public Coordinate() {
    }

    public Coordinate(String id, String user, List<CostumeCoordinate> costumes) {
        this.id = id;
        this.user = user;
        this.costumes = costumes;
    }

    public Coordinate(String id, List<CostumeCoordinate> costumes) {
        this.id = id;
        this.costumes = costumes;
    }

    public Coordinate(List<CostumeCoordinate> costumes) {
        this.costumes = costumes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<CostumeCoordinate> getCostumes() {
        return costumes;
    }

    public void setCostumes(List<CostumeCoordinate> costumes) {
        this.costumes = costumes;
    }
}
