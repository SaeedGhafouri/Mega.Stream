package com.serpider.service.megastream.util;

import android.content.Context;
import android.content.SharedPreferences;

public class DataSave {

    public void UserIdSave(Context context, String unique) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_UNIQUE", unique);
        editor.apply();
    }
    public String UserGetId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        return sharedPreferences.getString("USER_UNIQUE", "");
    }

}

