package com.personal_game.datn.ultilities;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

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
}
