package com.personal_game.datn.Request;

import com.personal_game.datn.Models.ColorObject;

public class Request_AddCart {
    private String costumeId;
    private ColorObject color;
    private String size;

    public Request_AddCart(String costumeId, ColorObject color, String size) {
        this.costumeId = costumeId;
        this.color = color;
        this.size = size;
    }
}
