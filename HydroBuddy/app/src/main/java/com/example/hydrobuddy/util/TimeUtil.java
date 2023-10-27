package com.example.hydrobuddy.util;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public static String getTimeAgo(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - timestamp;
        
        // Use DateUtils for relative time formatting
        return DateUtils.getRelativeTimeSpanString(
            timestamp,
            currentTime,
            DateUtils.MINUTE_IN_MILLIS
        ).toString();
    }
    public static String formatDate(Calendar calendar) {
        // Define your desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return dateFormat.format(calendar.getTime());
    }

    public static String formatDate(Date date) {
        // Define your desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return dateFormat.format(date);
    }
}