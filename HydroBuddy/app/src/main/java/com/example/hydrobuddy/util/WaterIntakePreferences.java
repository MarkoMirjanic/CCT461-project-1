package com.example.hydrobuddy.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class WaterIntakePreferences {
    private static final String PREF_NAME = "WaterIntakePrefs";

    // Store goal completion date, goal, and glasses drank for a specific date
    public static void saveGoalCompletion(Context context, String date, int goal, int glassesRank) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Save the goal, date, and glasses drank
        editor.putInt("goal_" + date, goal);
        editor.putString("date_" + date, date);
        editor.putInt("glasses_drank_" + date, glassesRank);

        // Save the goal completion date
        Set<String> goalCompletionDates = getGoalCompletionDates(context);
        goalCompletionDates.add(date);
        editor.putStringSet("goal_completion_dates", goalCompletionDates);

        editor.apply();
    }

    // Retrieve goal completion dates
    public static Set<String> getGoalCompletionDates(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getStringSet("goal_completion_dates", new HashSet<>());
    }

    // Retrieve goal for a specific date
    public static int getGoalForDate(Context context, String date) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getInt("goal_" + date, -1);
    }

    // Retrieve glasses rank for a specific date
    public static int getGlassesRankForDate(Context context, String date) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getInt("glasses_drank_" + date, 0);
    }
}
