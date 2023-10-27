package com.example.hydrobuddy.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static final String PREF_NAME = "MyAppPreferences";
    private static SharedPreferences sharedPreferences;

    public static void initialize(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Store a string value in SharedPreferences
    public static void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    // Retrieve a string value from SharedPreferences
    public static String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    // Store an integer value in SharedPreferences
    public static void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    // Retrieve an integer value from SharedPreferences
    public static int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Store an integer value in SharedPreferences
    public static void putLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    // Retrieve an integer value from SharedPreferences
    public static long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    // Store a boolean value in SharedPreferences
    public static void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    // Retrieve a boolean value from SharedPreferences
    public static boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Remove a specific key-value pair from SharedPreferences
    public static void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    // Clear all data in SharedPreferences
    public static void clear() {
        sharedPreferences.edit().clear().apply();
    }
}
