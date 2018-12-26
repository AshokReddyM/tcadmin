package com.byt_eye.tcadmin.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeUtil {

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }

}
