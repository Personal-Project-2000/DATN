package com.personal_game.datn.Backup;

import android.content.Context;
import android.content.Intent;

import com.personal_game.datn.Activity.CoordinateActivity;
import com.personal_game.datn.Activity.CostumeActivity;
import com.personal_game.datn.Models.Coordinate;
import com.personal_game.datn.Models.Costume;

public class Constant {
    public static String token_client = "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJRdWFuZ1N5IiwianRpIjoiMDcyYTIwNzEtNmNmMi00YzUxLWJkODQtYTc3M2VmMTI4MDlhIiwiaWF0IjoiMy8xNy8yMDIyIDU6MTI6NDMgUE0iLCJHcm91cCI6IlF1YW5nU3kiLCJFbWFpbCI6ImNvdmlkMjF0c3BAZ21haWwuY29tIiwiSG9zdGluZyI6ImNvdmlkMjF0c3Auc3BhY2UiLCJQaG9uZSI6ImNsaWVudCIsImV4cCI6MTczMzkxMTk2MywiaXNzIjoiUXVhbmdTeSIsImF1ZCI6IlF1YW5nU3kifQ.jKLDKoVDkNI5cf0lLkzIwPospM6djTq3SwJZTMztPEU";
    public static final int allBill = 1;
    public static final int billPaid = 7;
    public static final int billWait = 2;
    public static final int billHandle = 3;
    public static final int billTransported = 4;
    public static final int billComplete = 5;
    public static final int billCancel = 6;
    public static final String allBillId = "1";
    public static final String billPaidId = "7";
    public static final String billWaitId = "6233fceece455495b3c7b4e0";
    public static final String billHandleId = "6233fd68ce455495b3c7b4e1";
    public static final String billTransportedId = "6233fd77ce455495b3c7b4e2";
    public static final String billCompleteId = "6233fd7fce455495b3c7b4e3";
    public static final String billCancelId = "6233fd8ace455495b3c7b4e4";

    public static final String shirtId="623338f0c02ad9fa46582a5a";
    public static final String trousersId="623338bac02ad9fa46582a55";
    public static final String shoeId="623338fec02ad9fa46582a5c";
    public static final String hatId="623338f7c02ad9fa46582a5b";
    public static final String bagId="6233391ec02ad9fa46582a5d";

    public static final String unitHeight = "cm)";
    public static final String unitWeight = "kg)";

    public static final int codeMinus = 1;
    public static final int codeSelect = 2;
    public static final int codePlus = 3;

    public static final String tokenLocation = "a2f375b0-d4d4-11ec-ac32-0e0f5adc015a";
    public static final int shop_id = 2853871;
    public static final int shop_district = 1455;

    public static final int paymentDefault = 4;
    public static final int paymentZalo = 5;
    public static final int paymentMoMo = 6;

    public static void SwitchCostumeActivity(Context context, String costumeId){
        Intent intent = new Intent(context, CostumeActivity.class);
        intent.putExtra("costumeId", costumeId);
        context.startActivity(intent);
    }
}
