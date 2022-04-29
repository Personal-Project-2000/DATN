package com.personal_game.datn.ultilities;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ConvertMoney {
    public static String intConvertMoney( int value){
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        return formatter.format(value)+"đ";
    }

    public static String longConvertMoney( long value){
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        return formatter.format(value)+"đ";
    }
}
