package com.personal_game.datn.ultilities;

import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.util.Date;

public class RangeTime {
    public static long getBetweenDays (Date date1, Date date2){
        long getDiff = date2.getTime() - date1.getTime();

        return TimeUnit.MILLISECONDS.toDays(getDiff);
    }

    public static long getBetweenDayToCurrent (String date){
        Date date1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date2 = new Date();

        long millis=System.currentTimeMillis();
        java.sql.Date date4=new java.sql.Date(millis);

        String temp[] = (date2+"").split(" ");
        String temp1 = date4+" "+temp[3];

        try {
            date2 = simpleDateFormat.parse(temp1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long getDiff = date2.getTime() - date1.getTime();

        return TimeUnit.MILLISECONDS.toDays(getDiff);
    }

    //hàm dành cho tính khoảng cách thời gian khuyến mãi
    public static long getBetweenDayToNow(String time){
        String tempInput[] = time.split("T");
        tempInput[1] = tempInput[1].replace(".", "T");
        String tempInput1[] = (tempInput[1]+"").split("T");
        Date inputDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

        try {
            inputDate = simpleDateFormat.parse(tempInput[0]+" "+tempInput1[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date2 = new Date();

        long millis=System.currentTimeMillis();
        java.sql.Date date4=new java.sql.Date(millis);

        String temp[] = (date2+"").split(" ");
        String temp1 = date4+" "+temp[3];

        try {
            date2 = simpleDateFormat.parse(temp1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long getDiff = inputDate.getTime() - date2.getTime();

        return TimeUnit.MILLISECONDS.toMillis(getDiff);
    }

    public static boolean checkRangeEvent(String startTime, String endTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

        Date dateNow = new Date();

        long millis=System.currentTimeMillis();
        java.sql.Date date4=new java.sql.Date(millis);

        String temp[] = (dateNow+"").split(" ");
        String temp1 = date4+" "+temp[3];

        try {
            dateNow = simpleDateFormat.parse(temp1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String startTime1[] = startTime.split("T");
        startTime1[1] = startTime1[1].replace(".", "T");
        String startTime2[] = (startTime1[1]+"").split("T");
        Date inputDate1 = null;

        try {
            inputDate1 = simpleDateFormat.parse(startTime1[0]+" "+startTime2[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String endTime1[] = endTime.split("T");
        endTime1[1] = endTime1[1].replace(".", "T");
        String endTime2[] = (startTime1[1]+"").split("T");
        Date inputDate2 = null;

        try {
            inputDate2 = simpleDateFormat.parse(endTime1[0]+" "+endTime2[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long getDiff = dateNow.getTime() - inputDate1.getTime();

        if(getDiff < 0){
            return false;
        }else{
            getDiff = dateNow.getTime() - inputDate2.getTime();

            if(getDiff > 0){
                return false;
            }
        }

        return true;
    }
}
