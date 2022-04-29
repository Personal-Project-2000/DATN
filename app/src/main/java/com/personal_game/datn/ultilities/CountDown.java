package com.personal_game.datn.ultilities;

public class CountDown {
    public static String countDownTime(long milis){
        int seconds = (int) (milis / 1000) % 60;
        int minutes = (int) ((milis / (1000 * 60)) % 60);
        int hours = (int) ((milis / (1000 * 60 * 60)) % 24);
        int days = (int) ((milis / (1000 * 60 * 60)) / 24);

        if (days > 0)
            return days+" ngày";
        else
            if(hours > 0)
                return hours + ":" + minutes + ":" + seconds;
            else
                return minutes + ":" + seconds;
    }

    public static int day(long milis){
        return (int) ((milis / (1000 * 60 * 60)) / 24);
    }
}
