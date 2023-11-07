package com.serpider.service.megastream.util;

import android.content.Context;
import android.content.SharedPreferences;

public class DataSave {

    static String KEY_SETTINGS = "SETTINGS_APP";
    static String IS_READ = "IS_READ";

    public void UserIdSave(Context context, int unique) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER_UNIQUE", unique);
        editor.apply();
    }
    public int UserGetId(Context context) {
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

}

