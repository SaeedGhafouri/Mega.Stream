package com.serpider.service.megastream.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DataSave {

    public static String KEY_SETTINGS = "SETTINGS_APP";
    static String IS_READ = "IS_READ";
    static String IS_POWER = "IS_POWER";
    public static String SUP_PHONE = "SUP_PHONE";
    public static String SUP_EMAIL = "SUP_EMAIL";
    public static String SUP_TELEGRAM = "SUP_TELEGRAM";
    public static String SUP_INSTA = "SUP_INSTA";
    public static String SUP_X = "SUP_X";
    public static String COUNT_ITEM = "SUP_X";

    public void UserIdSave(Context context, int unique) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER_UNIQUE", unique);
        editor.apply();
    }
    public static int UserGetId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("USER_UNIQUE", 0);
    }

    /*is read welcome*/
    public static void isReady(Context context, boolean isRead) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_READ, isRead);
        editor.apply();
    }
    public static boolean getIsReady(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_READ, false);
    }

    public static void settingPerfence(Context context, boolean power, String phone, String email, String telegram, String instagram, String x, int countItem) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_POWER, power);
        editor.putString(SUP_PHONE, phone);
        editor.putString(SUP_EMAIL, email);
        editor.putString(SUP_TELEGRAM, telegram);
        editor.putString(SUP_INSTA, instagram);
        editor.putString(SUP_X, x);
        editor.putInt(COUNT_ITEM, countItem);
        editor.apply();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE);
    }

    //public static void loadPrefence(Context)

}

