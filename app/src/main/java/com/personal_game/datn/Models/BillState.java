package com.personal_game.datn.Models;

import java.io.Serializable;

public class BillState implements Serializable {
    private String Id ;
    private String Name ;
    private String Description ;

    public BillState(String id, String name, String description) {
        Id = id;
        Name = name;
        Description = description;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
