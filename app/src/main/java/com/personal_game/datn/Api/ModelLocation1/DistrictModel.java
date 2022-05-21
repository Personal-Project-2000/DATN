package com.personal_game.datn.Api.ModelLocation1;

public class DistrictModel {
    private int DistrictID;
    private String DistrictName;

    public DistrictModel(int districtID, String districtName) {
        DistrictID = districtID;
        DistrictName = districtName;
    }

    public int getDistrictID() {
        return DistrictID;
    }

    public void setDistrictID(int districtID) {
        DistrictID = districtID;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }
}
