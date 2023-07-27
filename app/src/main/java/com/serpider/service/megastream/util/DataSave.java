package com.serpider.service.megastream.util;

import android.content.Context;
import android.content.SharedPreferences;

public class DataSave {

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

}

